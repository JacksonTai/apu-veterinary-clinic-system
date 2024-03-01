const errorMessage = 'An unknown error occurred. Please try again later.';
const successCallback = (data) => {
    try {
        const searchError = document.getElementById('searchError');
        const searchResult = document.getElementById('searchResult');

        if (data.hasOwnProperty('id') && data.hasOwnProperty('fullName')) {

            searchResult.textContent = data.fullName;
            searchResult.classList.remove('d-none');
            searchResult.href = contextPath + endpoints.VIEW_STAFF + '?id=' + data.id;

            searchError.textContent = '';
            searchError.style.color = '';
        }
        if (data.hasOwnProperty('error')) {
            searchError.textContent = data.error;
            searchError.style.color = 'red';
            searchResult.classList.add('d-none');
        }
    } catch (e) {
        document.getElementById('searchError').textContent = errorMessage;
        document.getElementById('searchError').style.color = 'red';
    }
};

const errorCallback = (error) => {
    const searchErrorSpan = document.getElementById('searchError');
    searchErrorSpan.textContent = errorMessage;
    searchErrorSpan.style.color = 'red';
};

document.addEventListener("DOMContentLoaded", () => {
    submitForm(document.getElementById('searchStaffForm'), contextPath + endpoints.SEARCH_STAFF,
        successCallback, errorCallback);
});