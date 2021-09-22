const options = {
    method: 'GET',
    headers: {
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem('Authorization')
    }
}
const urlAssets = "http://localhost:8080/assets"
const urlAbbreviations = "http://localhost:8080/assets/abbreviations"
let assetList = [];

showAllAssets()

populateDropdownWithAbbreviations()


document.getElementById("buy").addEventListener("click", function (event) {
    event.preventDefault()

    let asset = String(document.querySelector("#selectAsset").value)
    let assetName = assetList[asset];
    let amount = Number(document.querySelector("#amount").value)
    let data = "?assetAbbr=" + assetName + "&assetAmount=" + amount

    const urlBuy = "http://localhost:8080/buyassetnow" + data;
    const optionsPost = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('Authorization')
        }
    }
    fetch(urlBuy, optionsPost)
        .then(response => {
            if (response.ok) {
                response.text(); //TODO uitlegd krijgen wat dit doet
                alert("Assets gekocht")
                //TODO blijven we op hetzelfde scherm? Gaan we elders heen? Wat ziet de gebruiker?
            } else if (!response.ok) {
                alert("Failed to buy assets")
                console.log("Failed to buy assets")
            }
        })
        .catch((error) => {
            console.log("Error " + error);
        })
})


function showAllAssets() {
    document.getElementById("allAssets")
    {
        fetchGenericTable(urlAssets, options, "#assetTable");
    }
}

function fetchGenericTable(url, options, tableId) {
    fetch(url, options)
        .then(response => {
            if (response.ok) {
                response.json()
                    .then(json => {
                        let jason = json;
                        let table = document.querySelector(tableId);
                        table.innerHTML = "";
                        let data = Object.keys(jason[0]);
                        generateTableHead(table, data);
                        generateTable(table, jason);
                    })
                    .catch((error) => {
                        console.error('Error' + error);
                    })
            } else {
                console.log('Failed to fetch data for table')
            }
        })
}

function generateTableHead(table, data) {
    let thead = table.createTHead();
    let row = thead.insertRow();
    for (let key of data) {
        let keyCapital = key.charAt(0).toUpperCase() + key.slice(1)
        let th = document.createElement("th");
        let text = document.createTextNode(keyCapital);
        th.appendChild(text);
        row.appendChild(th);
    }
}

function generateTable(table, data) {
    for (let element of data) {
        let row = table.insertRow();
        if (element === null) {
            element = "-"
        }
        for (let key in element) {
            let cell = row.insertCell();
            let text = document.createTextNode(element[key]);
            cell.appendChild(text);
        }
    }
}

function populateDropdownWithAbbreviations() {
    fetch(urlAbbreviations, options)
        .then(response => {
            if (response.ok) {
                response.json()
                    .then(json => {
                        console.log(json);
                        //let list = json;
                        assetList = json;
                        let dropdown = document.querySelector("#selectAsset");
                        for (let index in assetList) {
                            dropdown.options[dropdown.options.length] = new Option(assetList[index], index);
                        }
                    })
                    .catch((error) => {
                        console.error('Error' + error);
                    })
            } else {
                console.log('Failed to fetch AbbreviationsList')
            }
        })
}



/*let abbreviation = "?abbreviation" + abbreviation
let url = "http://localhost:8080/assets/doge"*/

/*let url = new URL(window.location.href)
console.log(url.origin)
const options = {
    method: "POST",
    headers: {
        "Content-type": "application/json"
    }
}*/

//relatieve url:
/*fetch(`${url.origin}/{abbreviation}`)
    .then(response => response.json())
    .then(data => console.log(data));*/

/*
createCustomDropdown()
//custom dropdown menu:
function createCustomDropdown() {
    let x, i, j, length, ll, selectElement, a, b, c;
    x = document.getElementsByClassName("custom-select");
    length = x.length;
    for (i = 0; i < length; i++) {
        selectElement = x[i].getElementsByTagName("select")[0];
        ll = selectElement.length;
        a = document.createElement("DIV");
        a.setAttribute("class", "select-selected");
        a.innerHTML = selectElement.options[selectElement.selectedIndex].innerHTML;
        x[i].appendChild(a);
        b = document.createElement("DIV");
        b.setAttribute("class", "select-items select hide");
        for (j = 1; j < ll; j++) {
            c = document.createElement("DIV");
            c.innerHTML = selectElement.options[j].innerHTML;
            c.addEventListener("click", function (e) {
                let y, i, k, s, h, sl, yl;
                s = this.parentNode.parentNode.getElementsByTagName("select")[0];
                sl = s.length;
                h = this.parentNode.previousSibling;
                for (i = 0; i < sl; i++) {
                    if (s.options[i].innerHTML === this.innerHTML) {
                        s.selectedIndex = i;
                        h.innerHTML = this.innerHTML;
                        y = this.parentNode.getElementsByClassName("same-as-selected");
                        yl = y.length;
                        for (k = 0; k < yl; k++) {
                            y[k].removeAttribute("class");
                        }
                        this.setAttribute("class", "same-as-selected");
                        break;
                    }
                }
                h.click();
            });
            b.appendChild(c);
        }
        x[i].appendChild(b);
        a.addEventListener("click", function (e) {
            e.stopPropagation();
            closeAllSelect(this);
            this.nextSibling.classList.toggle("select-hide");
            this.classList.toggle("select-arrow-active");
        })
    }
}

function closeAllSelect(element) {
    let x, y, i, xl, yl, arrNo = [];
    x = document.getElementsByClassName("select-items");
    y = document.getElementsByClassName("select-selected");
    xl = x.length;
    yl = y.length;
    for (i = 0; i < yl; i++) {
        if (element === y[i]) {
            arrNo.push(i)
        } else {
            y[i].classList.remove("select-arrow-active");
        }
    }
    for (i = 0; i < xl; i++) {
        if (arrNo.indexOf(i)) {
            x[i].classList.add("select-hide");
        }
    }
}
document.addEventListener("click", closeAllSelect);
//end of custom dropdown
*/