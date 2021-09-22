

// async function fetchWithRetry(x, ...args) {
//     let firstTry = await x(...args);
//     if (firstTry.status === 403) {
//         console.log("toRefresh in ieder geval gestart")
//         const urlRefresh = "http://localhost:8080/refresh"
//         const options = {
//             method: 'GET',
//             headers: {
//                 'Content-Type': 'application/json',
//                 'Authorization': localStorage.getItem('refresh_token')
//             }
//         }
//        let refreshTry = await fetch(urlRefresh, options);
//                 if (refreshTry.status === 200) {
//                     localStorage.setItem('Authorization', refreshTry.headers.get('Authorization'));
//                     localStorage.setItem('refresh_token', refreshTry.headers.get('refresh_token'));
//                     updateHeaderOptions();
//                     return x(...args);
//                 } else {
//                     alert("Session expired. Login to regain access.")
//                     window.location.assign('http://localhost:8080/index.html')
//                 }
//             }
//     return firstTry;
// }
//
// function updateHeaderOptions() {
//     options.headers.Authorization = localStorage.getItem('Authorization');
// }
//
// export {fetchWithRetry, updateHeaderOptions}