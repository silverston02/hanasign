export interface User {
  id: string;
  name: string;
  email: string;
  phone?: string;
  company?: string;
  department?: string;
  position?: string;
  createdAt: Date;
  updatedAt: Date;
} 