document.addEventListener('DOMContentLoaded', function () {
    var form = document.getElementById('userForm');

    form.addEventListener('submit', function (event) {
        var isValid = true;

        var password = document.getElementById('validationPassword');
        var username = document.getElementById('validationUsername');
        var codigoUsuario = document.getElementById('validationCodigo');

        // Validar contraseña (vacía o mínimo 6 caracteres)
        if (password.value !== '' && password.value.length < 6) {
            password.classList.add('is-invalid');
            isValid = false;
        } else {
            password.classList.remove('is-invalid');
            password.classList.add('is-valid');
        }

        // Validar nombre de usuario (vacío o mínimo 6 caracteres)
        if (username.value !== '' && username.value.length < 6) {
            username.classList.add('is-invalid');
            isValid = false;
        } else {
            username.classList.remove('is-invalid');
            username.classList.add('is-valid');
        }

        // Validar código de usuario (vacío o exactamente 7 caracteres)
        if (codigoUsuario.value !== '' && codigoUsuario.value.length !== 7) {
            codigoUsuario.classList.add('is-invalid');
            isValid = false;
        } else {
            codigoUsuario.classList.remove('is-invalid');
            codigoUsuario.classList.add('is-valid');
        }

        if (!isValid) {
            event.preventDefault();
            event.stopPropagation();
        }
    }, false);
});
