function addItem() {
    const list = document.getElementById('book-author-select')
    const value = list.value;
    if(value === "?") {
        return;
    }

    const sel = document.getElementById('book-author-selected-list')
    const option = document.createElement("option");
    option.value = value;
    sel.add(option);
    option.selected = true;

    const table = document.getElementById('authors_list').getElementsByTagName('tbody')[0];
    const rows = table.rows.length;
    const trElement = table.insertRow();

    if ((rows + 1) % 2 === 0) {
        trElement.classList.add('even');
    }

    const idColumn = trElement.insertCell();
    const fnColumn = trElement.insertCell();
    const snColumn = trElement.insertCell();
    const delColumn = trElement.insertCell();

    idColumn.appendChild(document.createTextNode(value));
    fnColumn.appendChild(document.createTextNode(authors[value].fn));
    snColumn.appendChild(document.createTextNode(authors[value].sn));
    delColumn.appendChild(createDelButton(value));

    list.value = "?";
}

function createDelButton(id) {
    const delButton = document.createElement("button");
    delButton.type = 'button';
    delButton.onclick = function () {
        rmItem(delButton.parentNode.parentNode, id)
    };
    delButton.textContent = 'Delete';
    return delButton;
}

function rmItem(trElement, id) {
    trElement.remove()

    const sel = document.getElementById('book-author-selected-list')
    for (let i = 0; i < sel.options.length; i++) {
        if (sel.options[i].value === id) {
            sel.options[i].remove();
        }
    }
}