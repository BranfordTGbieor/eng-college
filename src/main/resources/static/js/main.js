//This js file require more optimization, hence, it'll be optimized in due time


//Validation for the register page
function validateRegister(form) {

    //username checks
    if( form.username.value == "" ) {
        document.getElementById("username-label").innerHTML = "Please enter your username!"
        form.username.focus() ;
        return false;
    } else {

        if( form.username.value.length < 3 ) {
            document.getElementById("username-label").innerHTML = "Your username must not be three characters less!"
            form.username.focus() ;
            return false;
        }

    }


    //email checks
    var email = form.email.value;
    var emailPattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

    if (!(emailPattern.test(email))) {
        document.getElementById("email-label").innerHTML = "You have entered an invalid email address!";
        form.email.focus();
        return false;
    } else {

        if( form.email.value == "" ) {
            document.getElementById("email-label").innerHTML = "Email field can not be blank!";
            form.email.focus() ;
            return false;
        }
    }


    //password checks
    var password = form.password.value;
    var passwordPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;

    if (passwordPattern.test(password)) {
        document.getElementById("password-label").innerHTML = "Enter a password 5 or more characters long which includes a digit, and an upper and lower case letters.";
        form.password.focus();
        return false;
    } else {

        if( form.password.value == "" ) {
            document.getElementById("password-label").innerHTML = "Please enter a password to login!";
            form.password.focus() ;
            return false;
        }

    }


    return( true );
}




//Validation for the login page
function validateLogin(form) {

    //username checks
    if( form.username.value == "" ) {
        document.getElementById("username-label").innerHTML = "Please enter your username!"
        form.username.focus() ;
        return false;
    }

    if( form.username.value.length < 3 ) {
        document.getElementById("username-label").innerHTML = "Your username must not be three characters less!"
        form.username.focus() ;
        return false;
    }


    //password checks
    var password = form.password.value;
    var passwd =  /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;

    if (!(passwd.test(password))) {
        document.getElementById("password-label").innerHTML = "Enter a password 5 or more characters long which includes a digit, and an upper and lower case letters.";
        form.password.focus();
        return false;
    } else {
        if( form.password.value == "" ) {
            document.getElementById("password-label").innerHTML = "Please enter a password to login!";
            form.password.focus() ;
            return false;
        }
    }

    return( true );
}



//Validation email reset form
function validatePasswordResetEmail(form) {

    //email checks
    var email = form.email.value;
    var emailPattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

    if (emailPattern.test(email)) {
        return (true)
    } else {

        if( form.email.value == "" ) {
            document.getElementById("reset-email").innerHTML = "Email field can not be blank!";
            form.email.focus() ;
            return false;
        } else {

            document.getElementById("reset-email").innerHTML = "You have entered an invalid email address!";
            form.email.focus();
            return false;
        }
    }

    return( true );
}



//Validation for the search form
function validateSearch(form) {

        if( form.search.value == "" ) {
            document.getElementById("search-site").innerHTML = "Please enter a key-word to search!";
            form.search.focus() ;
            return false;
        }

    return( true );
}



//Validation for email subscription
function validateEmailSubscription(form) {

    //email checks
    var email = form.email.value;
    var emailPattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

    if (emailPattern.test(email)) {
        return (true)
    } else {

        if( form.email.value == "" ) {
            document.getElementById("subscribe-email").innerHTML = "Email field can not be blank!";
            form.email.focus() ;
            return false;
        } else {

            document.getElementById("subscribe-email").innerHTML = "You have entered an invalid email address!";
            form.email.focus();
            return false;
        }
    }

    return( true );
}



//Validation for the contact form
function validateContact(form) {

    var email = form.email.value;
    var emailPattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    var phone = form.phone.value;
    var phonePattern1 = /^\(?([0-9]{4})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{3})$/;
    var phonePattern2 = /^\+?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{3})[-. ]?([0-9]{3})$/;

    //username checks
    if( form.name.value == "" ) {
        document.getElementById("contact-fullname-label").innerHTML = "Please enter your full-name!"
        form.name.focus() ;
        return false;
    }

    //email checks
    if( form.email.value == "" ) {
        document.getElementById("contact-email-label").innerHTML = "Email field can not be blank!";
        form.email.focus() ;
        return false;
    } else {
        if (!(emailPattern.test(email))) {
            document.getElementById("contact-email-label").innerHTML = "You have entered an invalid email address!";
            form.email.focus();
            return false;
        }
    }

    //subject checks
    if( form.subject.value == "" ) {
        document.getElementById("contact-subject-label").innerHTML = "Please enter the subject of the message you wish to send.";
        form.subject.focus() ;
        return false;
    }

    //phone checks
    if( form.phone.value == "" ) {
        document.getElementById("contact-phone-label").innerHTML = "Please enter your phone-number.";
        form.phone.focus() ;
        return false;
    } else {
        if (!(phonePattern1.test(phone) || phonePattern2.test(phone))) {
            document.getElementById("contact-phone-label").innerHTML = "You have entered an incorrect phone-number!";
            form.phone.focus();
            return false;
        }
    }


    //message checks
    if( form.message.value == "" ) {
        document.getElementById("contact-message-label").innerHTML = "Please enter the the contents of your message";
        form.message.focus() ;
        return false;
    }


    return( true );
}



//Validation for the contact form
function validateUserDetails(form) {

    var emailPattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    var email = form.email.value;

    var phonePattern1 = /^\(?([0-9]{4})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{3})$/;
    var phonePattern2 = /^\+?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{3})[-. ]?([0-9]{3})$/;
    var phone = form.phone.value;

    var passwordPattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;
    var newPassword = form.newPassword.value;
    var retypedNewPassword = form.retypedNewPassword.value;

    //username checks
    if( form.username.value == "" ) {
        document.getElementById("user-username").innerHTML = "User-name field can not be blank!";
        form.username.focus() ;
        return false;
    }

    //email checks
    if( form.email.value == "" ) {
        document.getElementById("user-email").innerHTML = "Email field can not be blank!";
        form.email.focus() ;
        return false;
    } else {
        if (!(emailPattern.test(email))) {
            document.getElementById("user-email").innerHTML = "You have entered an invalid email address!";
            form.email.focus();
            return false;
        }
    }

    //generated password checks
    if( form.password.value == "" ) {
        document.getElementById("generated-password").innerHTML = "Please enter the generated password sent to your email.";
        form.password.focus() ;
        return false;
    }


    //phone checks
    if( form.phone.value == "" ) {
        document.getElementById("user-phone").innerHTML = "Please enter your phone-number.";
        form.phone.focus() ;
        return false;
    } else {
        if (!(phonePattern1.test(phone) || phonePattern2.test(phone))) {
            document.getElementById("user-phone").innerHTML = "You have entered an incorrect phone-number!";
            form.phone.focus();
            return false;
        }
    }


    //first-name checks
    if( form.firstName.value == "" ) {
        document.getElementById("last-name").innerHTML = "Please enter your first-name."
        form.firstName.focus() ;
        return false;
    }

    //last-name checks
    if( form.lastName.value == "" ) {
        document.getElementById("last-name").innerHTML = "Please enter your last-name."
        form.lastName.focus() ;
        return false;
    }


    //new password checks
    if (newPassword == "") {
        document.getElementById("new-password").innerHTML = "Please enter your new password!";
        form.newPassword.focus();
        return false;
    } else {
        if (!(passwordPattern.test(newPassword))) {
            document.getElementById("new-password").innerHTML = "Please enter a valid password that is at-least 6 characters long!";
            form.newPassword.focus();
            return false;
        }
    }


    //passwords match check
    if (retypedNewPassword == "") {
        document.getElementById("retyped-new-password").innerHTML = "Retype your new password!";
        form.retypedNewPassword.focus();
        return false;
    } else {
        if (retypedNewPassword != newPassword) {
            document.getElementById("retyped-new-password").innerHTML = "The passwords do not match!";
            form.retypedNewPassword.focus();
            return false;
        }
    }



    return( true );
}

