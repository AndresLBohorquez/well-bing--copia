(function () {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation')

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
})();

document.addEventListener('DOMContentLoaded', function () {
    // Selecciona el formulario y el campo de nombre de usuario
    var form = document.querySelector('.needs-validation');
    var usernameInput = form.querySelector('#validationUsername');

    // Agrega un event listener para validar el formulario antes de enviarlo
    form.addEventListener('submit', function (event) {
        if (usernameInput.value.length < 6) {
            // Si la longitud del nombre de usuario es menor que 6, evita enviar el formulario
            event.preventDefault();
            usernameInput.classList.add('is-invalid');
            usernameInput.nextElementSibling.style.display = 'block'; // Muestra el mensaje de error
        }
    });
    form.addEventListener('submit', function (event) {
        if (usernameInput.value.length < 6) {
            // Si la longitud del nombre de usuario es menor que 6, evita enviar el formulario
            event.preventDefault();
            usernameInput.classList.remove('valid-feedback');
            usernameInput.nextElementSibling.style.display = 'none'; // Muestra el mensaje de error
        }
    });

    // Agrega un event listener para validar el campo mientras el usuario escribe
    usernameInput.addEventListener('input', function () {
        if (usernameInput.value.length >= 6) {
            usernameInput.classList.remove('is-invalid');
            usernameInput.nextElementSibling.style.display = 'none'; // Oculta el mensaje de error
        }
    });
    usernameInput.addEventListener('input', function () {
        if (usernameInput.value.length >= 6) {
            usernameInput.classList.remove('valid-feedback');
            usernameInput.nextElementSibling.style.display = 'block'; // Oculta el mensaje de error
        }
    });
});


document.addEventListener('DOMContentLoaded', function () {
    // Selecciona el formulario y el campo de nombre de usuario
    var form = document.querySelector('.needs-validation');
    var passwordInput = form.querySelector('#validationPassword');

    // Agrega un event listener para validar el formulario antes de enviarlo
    form.addEventListener('submit', function (event) {
        if (passwordInput.value.length < 6) {
            // Si la longitud de la contraseña es menor que 6, evita enviar el formulario
            event.preventDefault();
            passwordInput.classList.add('is-invalid');
            passwordInput.nextElementSibling.style.display = 'block'; // Muestra el mensaje de error
        }
    });
    form.addEventListener('submit', function (event) {
        if (passwordInput.value.length < 6) {
            // Si la longitud de la contraseña es menor que 6, evita enviar el formulario
            event.preventDefault();
            passwordInput.classList.remove('valid-feedback');
            passwordInput.nextElementSibling.style.display = 'none'; // Muestra el mensaje de error
        }
    });

    // Agrega un event listener para validar el campo mientras el usuario escribe
    passwordInput.addEventListener('input', function () {
        if (passwordInput.value.length >= 6) {
            passwordInput.classList.remove('is-invalid');
            passwordInput.nextElementSibling.style.display = 'none'; // Oculta el mensaje de error
        }
    });
    passwordInput.addEventListener('input', function () {
        if (passwordInput.value.length >= 6) {
            passwordInput.classList.remove('valid-feedback');
            passwordInput.nextElementSibling.style.display = 'block'; // Oculta el mensaje de error
        }
    });
});