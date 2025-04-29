import { User } from './user';

export enum ContractStatus {
  WAITING = 'waiting',
  IN_PROGRESS = 'in_progress',
  COMPLETED = 'completed',
  REJECTED = 'rejected',
  EXPIRED = 'expired',
  CANCELED = 'canceled'
}

export interface Signer {
  id: string;
  name: string;
  email: string;
  phone?: string;
  signatureImage?: string;
  signedAt?: Date;
  status: ContractStatus;
}

export interface ContractDocument {
  id: string;
  filename: string;
  fileUrl: string;
  fileType: string;
  uploadedAt: Date;
}

export interface Contract {
  id: string;
  title: string;
  description?: string;
  creator: User;
  documents: ContractDocument[];
  signers: Signer[];
  status: ContractStatus;
  createdAt: Date;
  updatedAt: Date;
  expiresAt?: Date;
  completedAt?: Date;
  isDeleted: boolean;
}

export class ContractService {
  constructor() {}

  async createContract(data: {
    title: string;
    description?: string;
    creator: User;
    documents: File[];
    signers: Omit<Signer, 'signedAt' | 'status'>[];
    expiresAt?: Date;
  }): Promise<Contract> {
    // 계약 생성 로직
    throw new Error('Not implemented');
  }

  async uploadContractDocument(contractId: string, file: File): Promise<ContractDocument> {
    // 계약서 파일 업로드 로직
    throw new Error('Not implemented');
  }

  async getContract(contractId: string): Promise<Contract> {
    // 계약 조회 로직
    throw new Error('Not implemented');
  }

  async updateContract(contractId: string, data: Partial<Contract>): Promise<Contract> {
    // 계약 수정 로직
    throw new Error('Not implemented');
  }

  async deleteContract(contractId: string): Promise<void> {
    // 계약 삭제 로직
    throw new Error('Not implemented');
  }

  async signContract(contractId: string, signerId: string, signatureData: {
    signatureImage: string;
  }): Promise<Contract> {
    // 계약 서명 로직
    throw new Error('Not implemented');
  }

  async rejectContract(contractId: string, signerId: string, reason: string): Promise<Contract> {
    // 계약 거절 로직
    throw new Error('Not implemented');
  }

  async cancelContract(contractId: string): Promise<Contract> {
    // 계약 취소 로직
    throw new Error('Not implemented');
  }

  async getContractHistory(contractId: string): Promise<{
    action: string;
    actor: User;
    timestamp: Date;
    details: any;
  }[]> {
    // 계약 히스토리 조회 로직
    throw new Error('Not implemented');
  }

  async sendContractReminder(contractId: string, signerId: string): Promise<void> {
    // 서명 요청 알림 발송 로직
    throw new Error('Not implemented');
  }

  async validateContractDocument(file: File): Promise<boolean> {
    // 계약서 파일 유효성 검증 로직
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
} 