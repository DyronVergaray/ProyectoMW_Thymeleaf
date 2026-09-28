document.querySelectorAll('.btn-toggle').forEach(function (btn) {
    btn.addEventListener('click', function () {
      var input = btn.previousElementSibling;

      if (input.disabled) {
        // Habilitar edición
        input.disabled = false;
        input.focus();
        btn.textContent = 'Guardar';
        btn.classList.remove('btn-outline-primary');
        btn.classList.add('btn-success');
      } else {
        // Guardar y bloquear de nuevo
        input.disabled = true;
        btn.textContent = 'Cambiar';
        btn.classList.remove('btn-success');
        btn.classList.add('btn-outline-primary');
      }
    });
  });