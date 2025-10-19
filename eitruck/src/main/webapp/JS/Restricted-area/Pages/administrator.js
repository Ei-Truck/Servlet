document.addEventListener('DOMContentLoaded', function() {
    // Elementos do modal único
    const addBtn = document.getElementById('add-btn');
    const modal = document.getElementById('crud-modal');
    const closeBtn = document.querySelector('.close');
    const cancelBtn = document.getElementById('cancel-btn');
    const saveBtn = document.getElementById('save-btn');
    const form = document.getElementById('crud-form');
    const modalTitle = document.getElementById('modal-title');
    const adminIdInput = document.getElementById('admin-id');
    const actionTypeInput = document.getElementById('action-type');

    // Abrir modal para adicionar
    addBtn.addEventListener('click', function() {
        modalTitle.textContent = 'Adicionar Administrador';
        form.reset();
        adminIdInput.value = '';
        actionTypeInput.value = 'cadastrar';

        // Tornar o campo senha obrigatório no cadastro
        form.querySelector('[name="senha"]').required = true;

        modal.style.display = 'block';
    });

    // Fechar modal
    closeBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });

    cancelBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });

    // Abrir modal para editar
    document.querySelectorAll('.btn-edit').forEach(button => {
        button.addEventListener('click', function() {
            const id = this.getAttribute('data-id');
            const nome = this.getAttribute('data-nome');
            const cpf = this.getAttribute('data-cpf');
            const email = this.getAttribute('data-email');

            modalTitle.textContent = 'Editar Administrador';
            adminIdInput.value = id;
            actionTypeInput.value = 'editar';

            // Preencher os campos do formulário
            form.querySelector('[name="nome_completo"]').value = nome;
            form.querySelector('[name="cpf"]').value = cpf;
            form.querySelector('[name="email"]').value = email;

            // Senha não é obrigatória na edição
            form.querySelector('[name="senha"]').required = false;
            form.querySelector('[name="senha"]').value = '';
            form.querySelector('[name="senha"]').placeholder = 'Deixe em branco para manter a senha atual';

            modal.style.display = 'block';
        });
    });

    // Salvar (tanto adicionar quanto editar)
    saveBtn.addEventListener('click', function() {
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

    // Fechar modal ao clicar fora
    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
});