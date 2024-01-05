async function allBooks() {
    try {
        const response = await fetch("http://localhost:8080/api/v1/books");
        const jsonBook = await response.json();

        if (jsonBook.length > 0) {
            let temp = "";
            jsonBook.forEach((book) => {
                temp += "<tr>";
                temp += "<td>" + book.id + "</td>";
                temp += "<td>" + book.title + "</td>";
                temp += "<td>" + `<button onclick="updateBookForm(${book.id})" type="button">Редактировать</button>` + "</td>";
            })
            document.getElementById("booksTable").innerHTML = temp;
        }
    } catch (e) {
        console.error(e);
    }
}

allBooks();
