const URL = window.location.href;

getToken();

// deze functie doet nog twee dingen: getToken en goede form selecteren
function getToken(){
    const stringUrl = URL.split(`=`)
    const token = stringUrl[1];
    console.log(token);
    if (token === undefined) {
        document.getElementById('submitNewPasswordForm').style.display="none"
    } else {
        localStorage.setItem('Authorization', token);
        document.getElementById('sumbitEmailForm').style.display="none"
    }
}

document.querySelector('#resetPassword').addEventListener(`click`,
    function (event){
        event.preventDefault() // anders wordt de gebruiker naar andere pagina geleidt
        // // selecteer inputs en zet waarden omde normale submit functie uitgevoerd


        let password = String(document.querySelector('#passwordResetForm').value)
        let passwordConf = String(document.querySelector('#passwordResetFormConf').value)
        let isPasswordValid = isPasswordOK(password);
        let isPasswordConfValid = isPasswordOK(passwordConf);
        let validPassword = ((isPasswordValid && isPasswordConfValid) && (password === passwordConf));

        if (!validPassword) {

            //TODO We moeten nog functionaliteit voor wanneer de wachtwoorden NIET kloppen volgens onze richtlijnen OF
            // als de wachtwoorden niet gelijk zijn aan elkaar
            console.log('Password(s) do not meet requirements');
            alert('Password(s) do not meet requirements');
        }

        else {

            // Token uit url halen (getToken aanroepen ipv strings hieronder)
            // zie exchange.js voor een beter voorbeeld.
            const stringUrl = URL.split(`=`)
            const token = stringUrl[1];
            console.log(token);
            let data = '?password=' + password
            const url = `http://localhost:8080/api/forgot/submit` + data;
            const options = {
                method: `POST`,
                headers: {
                    'Content-Type': 'application/json',
                    // token toevoegen aan de header (want @RequestHeader)
                    'Authorization': token
                    // 'Authorization': localStorage.getItem('Authorization')
                }
            }

            fetch(url, options)
                .then(response => {
                    if (response.ok) {
                        response.text();
                        alert("New password saved.")
                        window.location.assign("http://localhost:8080/index.html")
                    } else if (!response.ok) {
                        console.log('Failed to save new password')
                    }
                })
                .catch((error) => {
                    console.error('Error ' + error);
                })
        }
    })

document.getElementById('submitEmail').addEventListener('click',
    function (event) {
    event.preventDefault();

    let email = String(document.querySelector('#emailToSubmit').value);
    let data = '?email=' + email;
    const url = 'http://localhost:8080/api/forgot/password' + data;
        const options = {
            method: `POST`,
            headers: {
                'Content-Type': 'application/json'
            }
        }
        fetch(url, options)
            .then(response => {
                if (response.ok) {
                    alert("Request for changing password send.")
                    window.location.assign("http://localhost:8080/index.html")
                } else if (response.status.valueOf()=== 418){
                    document.getElementById('inlogheader').innerHTML = "Email not found";
                }
            })
            .catch((error) => {
                console.error('Error ' + error);
            })
    })

const isPasswordOK = (password) => {
    const toCheck = new RegExp("^(?=.*[a-z])(?=.{8,})");
    return toCheck.test(password);
}
