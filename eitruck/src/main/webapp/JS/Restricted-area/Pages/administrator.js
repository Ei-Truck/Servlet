document.addEventListener('DOMContentLoaded', function() {
    // Elementos dos modais
    const addBtn = document.getElementById('add-btn');
    const addModal = document.getElementById('add-modal');
    const editModal = document.getElementById('edit-modal');
    const closeBtns = document.querySelectorAll('.close');
    const cancelAddBtn = document.getElementById('cancel-add-btn');
    const cancelEditBtn = document.getElementById('cancel-edit-btn');
    const saveAddBtn = document.getElementById('save-add-btn');
    const saveEditBtn = document.getElementById('save-edit-btn');

    // Opções de edição
    const editOptions = document.querySelectorAll('.edit-option');
    const editForms = document.querySelectorAll('.edit-form');

    // Variáveis para controle
    let currentEditOption = null;
    let currentAdminData = null;

    // Abrir modal para adicionar
    addBtn.addEventListener('click', function() {
        addModal.style.display = 'block';
    });

    // Fechar modais
    closeBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            addModal.style.display = 'none';
            editModal.style.display = 'none';
            resetEditModal();
        });
    });

    cancelAddBtn.addEventListener('click', function() {
        addModal.style.display = 'none';
    });

    cancelEditBtn.addEventListener('click', function() {
        editModal.style.display = 'none';
        resetEditModal();
    });

    // Salvar no modal de adicionar
    saveAddBtn.addEventListener('click', function() {
        const form = document.getElementById('add-form');
        if (form.checkValidity()) {
            form.submit();
        } else {
            alert('Por favor, preencha todos os campos obrigatórios.');
        }
    });

    // Editar administrador - abrir modal de opções
    document.querySelectorAll('.btn-edit').forEach(button => {
        button.addEventListener('click', function() {
            const id = this.getAttribute('data-id');
            const nome = this.getAttribute('data-nome');
            const cpf = this.getAttribute('data-cpf');
            const email = this.getAttribute('data-email');

            currentAdminData = { id, nome, cpf, email };
            document.getElementById('edit-admin-id').value = id;

            // Preencher todos os formulários com os dados atuais
            document.getElementById('edit-nome').value = nome;
            document.getElementById('edit-cpf').value = cpf;
            document.getElementById('edit-email').value = email;
            document.getElementById('edit-tudo-nome').value = nome;
            document.getElementById('edit-tudo-cpf').value = cpf;
            document.getElementById('edit-tudo-email').value = email;

            // Preencher os IDs nos formulários
            document.getElementById('nome-id').value = id;
            document.getElementById('cpf-id').value = id;
            document.getElementById('email-id').value = id;
            document.getElementById('senha-id').value = id;
            document.getElementById('tudo-id').value = id;

            editModal.style.display = 'block';
        });
    });

    // Selecionar opção de edição
    editOptions.forEach(option => {
        option.addEventListener('click', function() {
            const optionType = this.getAttribute('data-option');

            // Remover seleção anterior
            editOptions.forEach(opt => opt.classList.remove('selected'));
            // Esconder formulários anteriores
            editForms.forEach(form => form.classList.remove('active'));

            // Selecionar opção atual
            this.classList.add('selected');
            currentEditOption = optionType;

            // Mostrar formulário correspondente
            document.getElementById(`edit-${optionType}-form`).classList.add('active');

            // Mostrar botão salvar
            saveEditBtn.style.display = 'block';
        });
    });

    // Salvar no modal de editar
    saveEditBtn.addEventListener('click', function() {
        if (!currentEditOption) {
            alert('Por favor, selecione uma opção de edição.');
            return;
        }

        const form = document.getElementById(`${currentEditOption}-form`);
        if (form.checkValidity()) {
            form.submit();
        } else {
            alert('Por favor, preencha todos os campos obrigatórios.');
        }
    });

    // Excluir administrador
    document.querySelectorAll('.btn-delete').forEach(button => {
        button.addEventListener('click', function() {
            const id = this.getAttribute('data-id');

            if (confirm('Tem certeza que deseja excluir este administrador?')) {
                const form = document.createElement('form');
                form.method = 'post';
                form.action = 'administradores';

                const inputAction = document.createElement('input');
                inputAction.type = 'hidden';
                inputAction.name = 'action';
                inputAction.value = 'excluir';
                form.appendChild(inputAction);

                const inputId = document.createElement('input');
                inputId.type = 'hidden';
                inputId.name = 'id';
                inputId.value = id;
                form.appendChild(inputId);

                document.body.appendChild(form);
                form.submit();
            }
        });
    });

    // Limpar filtros
    document.getElementById('clear-filters').addEventListener('click', function() {
        document.querySelectorAll('.filter-input').forEach(input => {
            input.value = '';
        });
        document.getElementById('filter-form').submit();
    });

    // Fechar modais ao clicar fora
    window.addEventListener('click', function(event) {
        if (event.target === addModal) {
            addModal.style.display = 'none';
        }
        if (event.target === editModal) {
            editModal.style.display = 'none';
            resetEditModal();
        }
    });

    // Função para resetar o modal de edição
    function resetEditModal() {
        editOptions.forEach(opt => opt.classList.remove('selected'));
        editForms.forEach(form => form.classList.remove('active'));
        saveEditBtn.style.display = 'none';
        currentEditOption = null;
        currentAdminData = null;
    }
});