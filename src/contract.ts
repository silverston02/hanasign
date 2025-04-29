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

export interface ContractService {
  createContract(data: {
    title: string;
    description?: string;
    creator: User;
    documents: File[];
    signers: Omit<Signer, 'signedAt' | 'status'>[];
    expiresAt?: Date;
  }): Promise<Contract>;

  uploadContractDocument(contractId: string, file: File): Promise<ContractDocument>;

  getContract(contractId: string): Promise<Contract>;

  updateContract(contractId: string, data: Partial<Contract>): Promise<Contract>;

  deleteContract(contractId: string): Promise<void>;

  signContract(contractId: string, signerId: string, signatureData: {
    signatureImage: string;
  }): Promise<Contract>;

  rejectContract(contractId: string, signerId: string, reason: string): Promise<Contract>;

  cancelContract(contractId: string): Promise<Contract>;

  getContractHistory(contractId: string): Promise<{
    action: string;
    actor: User;
    timestamp: Date;
    details: any;
  }[]>;

  sendContractReminder(contractId: string, signerId: string): Promise<void>;

  validateContractDocument(file: File): Promise<boolean>;
} 