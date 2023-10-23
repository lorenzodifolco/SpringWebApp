const usernameEl = document.querySelector('#username');
const firstNameEl = document.querySelector('#firstName');
const lastNameEl = document.querySelector('#lastName');
const passwordEl = document.querySelector('#password');
const confirmPasswordEl = document.querySelector('#confirm');

const form = document.querySelector('#signup');


const checkUsername = () => {

    let valid = false;

    const username = usernameEl.value.trim();

    if (!isRequired(username)) {
        showError(usernameEl, 'Username cannot be blank.');
    } else if (!isPasswordSecure(username)) {
        showError(usernameEl, 'Username can include only letters, numbers or underscore');
    } else {
        showSuccess(usernameEl);
        valid = true;
    }
    return valid;
};


const checkFirstName = () => {
    let valid = false;
    const firstName = firstNameEl.value.trim();
    if (!isRequired(firstName)) {
        showError(firstNameEl, 'First name cannot be blank.');
    } else if (!isNameValid(firstName)) {
        showError(firstNameEl, 'First name is not valid.')
    } else {
        showSuccess(firstNameEl);
        valid = true;
    }
    return valid;
};

const checkLastName = () => {
    let valid = false;
    const lastName = lastNameEl.value.trim();
    if (!isRequired(lastName)) {
        showError(lastNameEl, 'Last name cannot be blank.');
    } else if (!isNameValid(lastName)) {
        showError(lastNameEl, 'Last name is not valid.')
    } else {
        showSuccess(lastNameEl);
        valid = true;
    }
    return valid;
};

const checkPassword = () => {
    let valid = false;


    const password = passwordEl.value.trim();

    if (!isRequired(password)) {
        showError(passwordEl, 'Password cannot be blank.');
    } else if (!isBetween(password.length, 8, 15)) {
        showError(passwordEl, 'Password must has at least 8 and max 15 characters');
    } else if (!isPasswordSecure(password)) {
        showError(passwordEl, 'Password can include only letters, numbers or underscore');
    } else {
        showSuccess(passwordEl);
        valid = true;
    }

    return valid;
};

const checkConfirmPassword = () => {
    let valid = false;
    const confirmPassword = confirmPasswordEl.value.trim();
    const password = passwordEl.value.trim();

    if (!isRequired(confirmPassword)) {
        showError(confirmPasswordEl, 'Please enter the password again');
    } else if (password !== confirmPassword) {
        showError(confirmPasswordEl, 'The password does not match');
    } else {
        showSuccess(confirmPasswordEl);
        valid = true;
    }

    return valid;
};

const isNameValid = (name) => {
    return /^[a-zA-Z\s]+$/.test(name);
};

const isPasswordSecure = (password) => {
    return /^[a-zA-Z0-9_]+$/.test(password);
};

const isRequired = value => value !== '';

const isBetween = (length, min, max) => !(length < min || length > max);


const showError = (input, message) => {
    const formField = input.parentElement;
    formField.classList.remove('success');
    formField.classList.add('error');

    const error = formField.querySelector('small');
    error.textContent = message;
};

const showSuccess = (input) => {
    const formField = input.parentElement;

    formField.classList.remove('error');
    formField.classList.add('success');

    const error = formField.querySelector('small');
    error.textContent = '';
}


form.addEventListener('submit', function (e) {
    e.preventDefault();

    let isFirstNameValid = checkFirstName(),
        isLastNameValid = checkLastName(),
        isUsernameValid = checkUsername(),
        isPasswordValid = checkPassword(),
        isConfirmPasswordValid = checkConfirmPassword();

    let isFormValid = isFirstNameValid && isLastNameValid &&
        isUsernameValid && isPasswordValid && isConfirmPasswordValid;

    if (isFormValid) {
        e.target.submit();
    }
});


const debounce = (fn, delay = 500) => {
    let timeoutId;
    return (...args) => {
        // cancel the previous timer
        if (timeoutId) {
            clearTimeout(timeoutId);
        }
        // set up a new timer
        timeoutId = setTimeout(() => {
            fn.apply(null, args)
        }, delay);
    };
};

form.addEventListener('input', debounce(function (e) {
    switch (e.target.id) {
        case 'firstName':
            checkFirstName();
            break;
        case 'lastName':
            checkLastName();
            break;
        case 'username':
            checkUsername();
            break;
        case 'password':
            checkPassword();
            break;
        case 'confirm':
            checkConfirmPassword();
            break;
    }

    if (checkFirstName() && checkLastName() && checkUsername() && checkPassword() && checkConfirmPassword()) {
        if (!document.getElementById("button").hasChildNodes()) {
            const btn = document.createElement("BUTTON");
            btn.setAttribute("type", "submit");
            btn.setAttribute("class", "btn btn-primary");
            btn.innerText = "Register";
            document.getElementById("button").appendChild(btn);
        }
    } else {
        document.getElementById("button").lastChild.remove();
    }
}));