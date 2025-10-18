document.addEventListener('DOMContentLoaded', function() {
    const addBtn = document.getElementById('add-btn');
    const modal = document.getElementById('crud-modal');
    const closeBtn = document.querySelector('.close');
    const cancelBtn = document.getElementById('cancel-btn');
    const saveBtn = document.getElementById('save-btn');
    const form = document.getElementById('crud-form');
    const modalTitle = document.getElementById('modal-title');
    const clearFiltersBtn = document.getElementById('clear-filters');

    // Abrir modal para adicionar
    addBtn.addEventListener('click', function() {
        modalTitle.textContent = 'Adicionar Administrador';
        form.reset();
        document.getElementById('admin-id').value = '';
        document.getElementById('action-type').value = 'cadastrar';
        modal.style.display = 'block';
    });

    // Fechar modal
    closeBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });

    cancelBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });

    // Salvar administrador
    saveBtn.addEventListener('click', function() {
        if (form.checkValidity()) {
            form.submit();
        } else {
            alert('Por favor, preencha todos os campos obrigatórios.');
        }
    });

    // Editar administrador
    document.querySelectorAll('.btn-edit').forEach(button => {
        button.addEventListener('click', function() {
            const id = this.getAttribute('data-id');
            const nome = this.getAttribute('data-nome');
            const cpf = this.getAttribute('data-cpf');
            const email = this.getAttribute('data-email');

            modalTitle.textContent = 'Editar Administrador';
            document.getElementById('admin-id').value = id;
            document.querySelector('input[name="nome_completo"]').value = nome;
            document.querySelector('input[name="cpf"]').value = cpf;
            document.querySelector('input[name="email"]').value = email;
            document.querySelector('input[name="senha"]').value = '';
            document.querySelector('input[name="senha"]').required = false;
            document.getElementById('action-type').value = 'editar';

            modal.style.display = 'block';
        });
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
    clearFiltersBtn.addEventListener('click', function() {
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