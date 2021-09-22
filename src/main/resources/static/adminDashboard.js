const urlBlockedUsers = "http://localhost:8080/blocked"

showBlockedUsers()

function showBlockedUsers() {
    document.querySelector("blockedUsers")
    {
        fetchGenericTable(urlBlockedUsers, options, "#blockedUsersTable");
    }
}

document.getElementById("adminFindUserSubmit").addEventListener("click", function (event){
    event.preventDefault()
    document.getElementById("adminFindUserSubmit").style.display="none"
    document.getElementById("blockButtons").style.display="block"


}, false)

