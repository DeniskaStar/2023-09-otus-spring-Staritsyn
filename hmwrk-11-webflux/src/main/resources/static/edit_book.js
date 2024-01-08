//Функция для заполнения модального окна данными о существующем пользователе
async function updateBookForm(id) {
    $("#editBook").modal();

    let currentBook = await (await fetch("http://localhost:8080/api/v1/books/" + id)).json();
    let authorsExists = await (await fetch("http://localhost:8080/api/v1/authors")).json();
    let genresExists = await (await fetch("http://localhost:8080/api/v1/genres")).json();

    $('#idEdit').val(currentBook.id);
    $('#titleEdit').val(currentBook.title);

    // Очищаем список авторов перед добавлением новых элементов
    $('#authorEdit').empty();
    // Добавляем каждого автора в список
    authorsExists.forEach(author => {
        let option = $('<option>').attr('value', author.id).text(author.fullName);
        // Если текущий автор совпадает с автором книги, выбираем его
        if (author.id === currentBook.author.id) {
            option.attr('selected', 'selected');
        }
        $('#authorEdit').append(option);
    });

    // Очищаем список жанров перед добавлением новых элементов
    $('#genresEdit').empty();
    // Добавляем каждый жанр в список
    const genreIds = currentBook.genres.map(it => it.id);
    genresExists.forEach(genre => {
        let option = $('<option>').attr('value', genre.id).text(genre.name);
        // Если текущий жанр совпадает с жанром книги, выбираем его
        if (genreIds.includes(genre.id)) {
            option.attr('selected', 'selected');
        }
        $('#genresEdit').append(option);
    });

    //Функция для вызова updateBook, при нажатии кнопку "Редактировать" в модальном окне
    buttonEditBook.onclick = function (event) {
        event.preventDefault()
        booksFormForEdit.id = id;
        booksFormForEdit.title = $('#titleEdit').val();
        booksFormForEdit.authorId = $('#authorEdit').val();
        booksFormForEdit.genreIds = $('#genresEdit').val();
        updateBook(id).then(allBooks);
    }
}

//Функция для отпрвки PUT запроса на сервер при нажатии на кнопку в модальном окне
async function updateBook(id) {
    try {
        await fetch("http://localhost:8080/api/v1/books/" + id, {
            method: "PUT",
            body: JSON.stringify(booksFormForEdit),
            headers: {
                "Content-Type": "application/json"
            }
        });
    } catch (e) {
        console.error(e);
    }
}

// Создаем пустую форму для редактирования книги
const booksFormForEdit = {
    title: "",
    authorId: 0,
    genreIds: [0]
}
