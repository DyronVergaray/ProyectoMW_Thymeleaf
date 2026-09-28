document.addEventListener("DOMContentLoaded", () => {
    const formPublicarVacante = document.getElementById("formPublicarVacante");

    if (formPublicarVacante) {
        formPublicarVacante.addEventListener("submit", (evento) => {
            evento.preventDefault();

            const modalElement = document.getElementById("modalPublicarVacante");
            const modal = bootstrap.Modal.getInstance(modalElement);
            modal.hide();

            formPublicarVacante.reset();
            alert("Vacante publicada correctamente.");
        });
    }
});
