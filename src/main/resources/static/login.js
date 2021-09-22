

document.querySelector('#submitButton').addEventListener(`click`,
    function (event){
        event.preventDefault() // anders wordt de gebruiker naar andere pagina geleidt
        // // selecteer inputs en zet waarden omde normale submit functie uitgevoerd

        let username = String(document.querySelector('#username').value)
        let password = String(document.querySelector('#password').value)

        // Stuurt username en password naar de server
        let data = '?username=' + username + '&password=' + password
        const url = `http://localhost:8080/login` + data;
        const options = {
            method: `POST`,
            headers: {
                'Content-Type': 'application/json'
            }
        }
        // Token komt terug als username & password worden erkend
       fetch(url,options)
            .then(response => {
                if (response.ok){
                response.text();
                localStorage.setItem('Authorization', response.headers.get('Authorization'));
                localStorage.setItem('refresh_token', response.headers.get('refresh_token'));
                window.location.assign("http://localhost:8080/dashboard.html")
            }else if (!response.ok) {
                    console.log('username and/or password are incorrect')
                }})
            .catch((error) => {
                console.error('Error ' + error);
            })

    })

document.querySelector('#signupButton').addEventListener('click', () => {
location.href = "/register.html"
});

document.querySelector('#forgotPasswordButton').addEventListener('click', () => {
    location.href = "/forgot-password.html" // andere pagina
});










