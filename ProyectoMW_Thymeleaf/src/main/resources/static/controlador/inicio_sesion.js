function inicio_sesion(){
    let correo = document.getElementById("exampleInputEmail1").value;
    let contraseña = document.getElementById("exampleInputPassword1").value;

    if(correo === "estudiante@hotmail.com" && contraseña === "estudiante123"){
        window.location.href = "estudiante.html";
    } else {
        alert("Correo o contraseña incorrecta");
        document.getElementById("error").textContent = "Correo o contraseña incorrecta";
    }
}