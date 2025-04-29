import { Contract, ContractDocument, ContractService, ContractStatus, Signer } from './contract';
import { User } from './user';
import { v4 as uuidv4 } from 'uuid';

export class ContractServiceImpl implements ContractService {
  private contracts: Map<string, Contract>;

  constructor() {
    this.contracts = new Map();
  }

  async createContract(data: {
    title: string;
    description?: string;
    creator: User;
    documents: File[];
    signers: Omit<Signer, 'signedAt' | 'status'>[];
    expiresAt?: Date;
  }): Promise<Contract> {
    const contractId = uuidv4();
    
    // 문서 업로드 처리
    const documents: ContractDocument[] = await Promise.all(
      data.documents.map(async (file) => {
        await this.validateContractDocument(file);
        return {
          id: uuidv4(),
          filename: file.name,
          fileUrl: await this.uploadFile(file),
          fileType: file.type,
          uploadedAt: new Date()
        };
      })
    );

    // 서명자 정보 초기화
    const signers: Signer[] = data.signers.map(signer => ({
      ...signer,
      status: ContractStatus.WAITING
    }));

    const contract: Contract = {
      id: contractId,
      title: data.title,
      description: data.description,
      creator: data.creator,
      documents,
      signers,
      status: ContractStatus.WAITING,
      createdAt: new Date(),
      updatedAt: new Date(),
      expiresAt: data.expiresAt,
      isDeleted: false
    };

    this.contracts.set(contractId, contract);
    return contract;
  }

  async uploadContractDocument(contractId: string, file: File): Promise<ContractDocument> {
    const contract = await this.getContract(contractId);
    await this.validateContractDocument(file);

    const document: ContractDocument = {
      id: uuidv4(),
      filename: file.name,
      fileUrl: await this.uploadFile(file),
      fileType: file.type,
      uploadedAt: new Date()
    };

    contract.documents.push(document);
    contract.updatedAt = new Date();
    this.contracts.set(contractId, contract);

    return document;
  }

  async getContract(contractId: string): Promise<Contract> {
    const contract = this.contracts.get(contractId);
    if (!contract) {
      throw new Error('Contract not found');
    }
    return contract;
  }

  async updateContract(contractId: string, data: Partial<Contract>): Promise<Contract> {
    const contract = await this.getContract(contractId);
    
    const updatedContract = {
      ...contract,
      ...data,
      updatedAt: new Date()
    };

    this.contracts.set(contractId, updatedContract);
    return updatedContract;
  }

  async deleteContract(contractId: string): Promise<void> {
    const contract = await this.getContract(contractId);
    contract.isDeleted = true;
    contract.updatedAt = new Date();
    this.contracts.set(contractId, contract);
  }

  async signContract(contractId: string, signerId: string, signatureData: {
    signatureImage: string;
  }): Promise<Contract> {
    const contract = await this.getContract(contractId);
    
    const signerIndex = contract.signers.findIndex(s => s.id === signerId);
    if (signerIndex === -1) {
      throw new Error('Signer not found');
    }

    contract.signers[signerIndex] = {
      ...contract.signers[signerIndex],
      signatureImage: signatureData.signatureImage,
      signedAt: new Date(),
      status: ContractStatus.COMPLETED
    };

    // 모든 서명자가 서명했는지 확인
    const allSigned = contract.signers.every(s => s.status === ContractStatus.COMPLETED);
    if (allSigned) {
      contract.status = ContractStatus.COMPLETED;
      contract.completedAt = new Date();
    }

    contract.updatedAt = new Date();
    this.contracts.set(contractId, contract);
    return contract;
  }

  async rejectContract(contractId: string, signerId: string, reason: string): Promise<Contract> {
    const contract = await this.getContract(contractId);
    
    const signerIndex = contract.signers.findIndex(s => s.id === signerId);
    if (signerIndex === -1) {
      throw new Error('Signer not found');
    }

    contract.signers[signerIndex].status = ContractStatus.REJECTED;
    contract.status = ContractStatus.REJECTED;
    contract.updatedAt = new Date();

    this.contracts.set(contractId, contract);
    return contract;
  }

  async cancelContract(contractId: string): Promise<Contract> {
    const contract = await this.getContract(contractId);
    
    contract.status = ContractStatus.CANCELED;
    contract.updatedAt = new Date();

    this.contracts.set(contractId, contract);
    return contract;
  }

  async getContractHistory(contractId: string): Promise<{
    action: string;
    actor: User;
    timestamp: Date;
    details: any;
  }[]> {
    // 실제 구현에서는 별도의 히스토리 테이블을 사용해야 합니다
    return [];
  }

  async sendContractReminder(contractId: string, signerId: string): Promise<void> {
    const contract = await this.getContract(contractId);
    const signer = contract.signers.find(s => s.id === signerId);
    
    if (!signer) {
      throw new Error('Signer not found');
    }

    // 실제 구현에서는 이메일 또는 알림 서비스를 통해 알림을 발송합니다
    console.log(`Sending reminder to ${signer.email} for contract ${contractId}`);
  }

  async validateContractDocument(file: File): Promise<boolean> {
    const validTypes = ['application/pdf', 'application/msword', 
                       'application/vnd.openxmlformats-officedocument.wordprocessingml.document'];
    const maxSize = 5 * 1024 * 1024; // 5MB

    if (!validTypes.includes(file.type)) {
      throw new Error('Invalid file type');
    }

    if (file.size > maxSize) {
      throw new Error('File size exceeds limit');
    }

    return true;
  }

  private async uploadFile(file: File): Promise<string> {
    // 실제 구현에서는 클라우드 스토리지에 파일을 업로드하고 URL을 반환합니다
    return `https://storage.example.com/contracts/${file.name}`;
  }
} 