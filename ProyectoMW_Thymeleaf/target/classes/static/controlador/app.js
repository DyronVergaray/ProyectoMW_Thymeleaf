document.addEventListener("DOMContentLoaded", () => {

    // Carga un componente HTML (navbar / sidebar) dentro de un contenedor.
    // Si la pagina no tiene ese contenedor (perfil.html arma su navbar del
    // lado servidor con Thymeleaf), no hace nada.
    const cargarComponente = (idContenedor, rutaArchivo, alCargar) => {
        const contenedor = document.getElementById(idContenedor);
        if (!contenedor) return;

        fetch(rutaArchivo)
            .then(response => {
                if (!response.ok) throw new Error("Error al cargar " + rutaArchivo);
                return response.text();
            })
            .then(html => {
                contenedor.innerHTML = html;
                if (alCargar) alCargar();
            })
            .catch(error => console.error(error));
    };

    cargarComponente("navbar-contenedor", "navbar.html");
    cargarComponente("sidebar-contenedor", "sidebar.html", () => {
        const btnCerrarSesion = document.getElementById("btnCerrarSesion");
        if (btnCerrarSesion) {
            btnCerrarSesion.addEventListener("click", (evento) => {
                const confirmar = confirm("¿Seguro que deseas cerrar sesión?");
                if (!confirmar) evento.preventDefault();
            });
        }
    });
});
