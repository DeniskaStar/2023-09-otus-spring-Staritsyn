// Функция, которая вызывается при нажатии на кнопку "Добавить пользователя"
async function createBookForm() {
    let authorsExists = await (await fetch("http://localhost:8080/api/v1/authors")).json();
    let genresExists = await (await fetch("http://localhost:8080/api/v1/genres")).json();

    authorsExists.forEach(author => {
        let option = $('<option>').attr('value', author.id).text(author.fullName);
        $('#authorNew').append(option);
    });

    genresExists.forEach(genre => {
        let option = $('<option>').attr('value', genre.id).text(genre.name);
        $('#genresNew').append(option);
    });

    buttonCreateBook.onclick = function () {
        bookEmptyForm.title = $('#titleNew').val();
        bookEmptyForm.authorId = $('#authorNew').val();
        bookEmptyForm.genreIds = $('#genresNew').val();
        newBook().then(allBooks);
    }
}

// Функция для отправки пост-метода на сервер
async function newBook() {
    try {
        await fetch("http://localhost:8080/api/v1/books", {
            method: "POST",
            body: JSON.stringify(bookEmptyForm),
            headers: {
                "Content-Type": "application/json"
            }
        });
    } catch (e) {
        console.error(e);
    }
}

// Создаем пустую форму для добавления пользователя
const bookEmptyForm = {
    title: "",
    authorId: 0,
    genreIds: [0]
}



