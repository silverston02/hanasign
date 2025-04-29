interface Contract {
    id: string;
    title: string;
    description: string;
    createdAt: Date;
    expiresAt: Date;
    status: 'waiting' | 'in-progress' | 'completed' | 'rejected';
    signers: Signer[];
    files: File[];
}

interface Signer {
    name: string;
    email: string;
    signed: boolean;
    signedAt?: Date;
}

class ContractManager {
    private contracts: Contract[] = [];

    constructor() {
        this.initializeEventListeners();
        this.loadMockData();
    }

    private initializeEventListeners(): void {
        // New Contract Button
        const newContractBtn = document.getElementById('newContractBtn');
        if (newContractBtn) {
            newContractBtn.addEventListener('click', () => this.showNewContractModal());
        }

        // Contract Form
        const contractForm = document.getElementById('contractForm') as HTMLFormElement;
        if (contractForm) {
            contractForm.addEventListener('submit', (e) => this.handleContractSubmit(e));
        }

        // Add Signer Button
        const addSignerBtn = document.getElementById('addSignerBtn');
        if (addSignerBtn) {
            addSignerBtn.addEventListener('click', () => this.addSignerInput());
        }

        // Status Filter
        const statusFilter = document.getElementById('statusFilter') as HTMLSelectElement;
        if (statusFilter) {
            statusFilter.addEventListener('change', () => this.filterContracts());
        }

        // Search Input
        const searchInput = document.getElementById('searchInput') as HTMLInputElement;
        if (searchInput) {
            searchInput.addEventListener('input', () => this.filterContracts());
        }

        // Close Modal Button
        const closeModalBtn = document.getElementById('closeModalBtn');
        if (closeModalBtn) {
            closeModalBtn.addEventListener('click', () => this.hideNewContractModal());
        }
    }

    private showNewContractModal(): void {
        const modal = document.getElementById('newContractModal');
        if (modal) {
            modal.classList.add('active');
        }
    }

    private hideNewContractModal(): void {
        const modal = document.getElementById('newContractModal');
        if (modal) {
            modal.classList.remove('active');
        }
        const form = document.getElementById('contractForm') as HTMLFormElement;
        if (form) {
            form.reset();
        }
        this.clearSignerInputs();
    }

    private clearSignerInputs(): void {
        const signersContainer = document.getElementById('signersContainer');
        if (signersContainer) {
            signersContainer.innerHTML = '';
            this.addSignerInput(); // Add one empty signer input
        }
    }

    private addSignerInput(): void {
        const signersContainer = document.getElementById('signersContainer');
        if (!signersContainer) return;

        const signerDiv = document.createElement('div');
        signerDiv.className = 'signer-input';
        signerDiv.innerHTML = `
            <input type="text" placeholder="이름" required>
            <input type="email" placeholder="이메일" required>
            <button type="button" class="btn secondary" onclick="this.parentElement.remove()">삭제</button>
        `;
        signersContainer.appendChild(signerDiv);
    }

    private handleContractSubmit(e: Event): void {
        e.preventDefault();
        const form = e.target as HTMLFormElement;
        const formData = new FormData(form);

        const signerInputs = document.querySelectorAll('.signer-input');
        const signers: Signer[] = Array.from(signerInputs).map(signerDiv => {
            const inputs = signerDiv.querySelectorAll('input');
            return {
                name: (inputs[0] as HTMLInputElement).value,
                email: (inputs[1] as HTMLInputElement).value,
                signed: false
            };
        });

        const newContract: Contract = {
            id: this.generateId(),
            title: formData.get('title') as string,
            description: formData.get('description') as string,
            createdAt: new Date(),
            expiresAt: new Date(formData.get('expiresAt') as string),
            status: 'waiting',
            signers: signers,
            files: Array.from(formData.getAll('files') as File[])
        };

        this.contracts.push(newContract);
        this.hideNewContractModal();
        this.renderContracts();
    }

    private generateId(): string {
        return Math.random().toString(36).substr(2, 9);
    }

    private filterContracts(): void {
        const statusFilter = document.getElementById('statusFilter') as HTMLSelectElement;
        const searchInput = document.getElementById('searchInput') as HTMLInputElement;
        
        let filteredContracts = this.contracts;

        if (statusFilter.value !== 'all') {
            filteredContracts = filteredContracts.filter(contract => 
                contract.status === statusFilter.value
            );
        }

        if (searchInput.value) {
            const searchTerm = searchInput.value.toLowerCase();
            filteredContracts = filteredContracts.filter(contract =>
                contract.title.toLowerCase().includes(searchTerm) ||
                contract.description.toLowerCase().includes(searchTerm)
            );
        }

        this.renderContracts(filteredContracts);
    }

    private renderContracts(contracts: Contract[] = this.contracts): void {
        const tbody = document.querySelector('.contract-table tbody');
        if (!tbody) return;

        tbody.innerHTML = '';
        contracts.forEach(contract => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${contract.title}</td>
                <td>${this.formatDate(contract.createdAt)}</td>
                <td>${this.formatDate(contract.expiresAt)}</td>
                <td><span class="status-badge status-${contract.status}">${this.getStatusText(contract.status)}</span></td>
                <td>
                    <button class="btn secondary" onclick="contractManager.viewContract('${contract.id}')">보기</button>
                    <button class="btn secondary" onclick="contractManager.deleteContract('${contract.id}')">삭제</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    }

    private formatDate(date: Date): string {
        return new Date(date).toLocaleDateString('ko-KR');
    }

    private getStatusText(status: Contract['status']): string {
        const statusMap = {
            'waiting': '대기중',
            'in-progress': '진행중',
            'completed': '완료',
            'rejected': '거절'
        };
        return statusMap[status];
    }

    public viewContract(id: string): void {
        const contract = this.contracts.find(c => c.id === id);
        if (!contract) return;
        
        // TODO: Implement contract view modal
        alert('계약서 상세 보기 기능은 추후 구현 예정입니다.');
    }

    public deleteContract(id: string): void {
        if (confirm('정말로 이 계약서를 삭제하시겠습니까?')) {
            this.contracts = this.contracts.filter(c => c.id !== id);
            this.renderContracts();
        }
    }

    private loadMockData(): void {
        // Add some mock data for testing
        this.contracts = [
            {
                id: 'mock1',
                title: '서비스 이용 계약서',
                description: '기본 서비스 이용 계약서입니다.',
                createdAt: new Date('2024-03-01'),
                expiresAt: new Date('2024-03-31'),
                status: 'waiting',
                signers: [
                    { name: '홍길동', email: 'hong@example.com', signed: false }
                ],
                files: []
            },
            {
                id: 'mock2',
                title: '비밀유지계약서',
                description: '프로젝트 관련 NDA',
                createdAt: new Date('2024-02-15'),
                expiresAt: new Date('2024-04-15'),
                status: 'in-progress',
                signers: [
                    { name: '김철수', email: 'kim@example.com', signed: true, signedAt: new Date('2024-02-16') },
                    { name: '이영희', email: 'lee@example.com', signed: false }
                ],
                files: []
            }
        ];
        this.renderContracts();
    }
}

// Initialize the contract manager when the DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    window.contractManager = new ContractManager();
}); 