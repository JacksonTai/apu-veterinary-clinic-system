const errorMessage = 'An unknown error occurred. Please try again later.';
const petSelect = document.getElementById("petId");
const customerDetails = document.getElementById("customerDetails");
const customerDetailsAjax = document.getElementById("customerDetailsAjax");
const disabledPetOption = document.getElementById("disabledPetOption");

customerDetailsAjax.addEventListener("blur", () => {
    customerDetails.value = customerDetailsAjax.value
})

const successCallback = (data) => {
    try {
        const searchError = document.getElementById('searchError');
        const searchResult = document.getElementById('searchResult');

        const selectPetId = document.getElementById("petId");
        Array.from(selectPetId.options)
            .filter(option => option.id !== "disabledPetOption")
            .forEach(option => option.remove());

        customerDetails.value = customerDetailsAjax.value;

        if (data.hasOwnProperty('id') && data.hasOwnProperty('fullName') && data.hasOwnProperty('pets')) {

            const customerId = document.getElementById("customerId");

            searchResult.textContent = data.fullName;
            searchResult.classList.remove('d-none');
            searchResult.href = contextPath + endpoints.VIEW_CUSTOMER + '?id=' + data.id;

            searchError.textContent = '';
            searchError.style.color = '';

            data.pets.forEach(pet => {
                let exists = Array.from(petSelect.options).some(option => option.value === pet.petId);
                if (!exists) {
                    const option = document.createElement("option");
                    option.textContent = pet.name;
                    option.value = pet.petId;
                    petSelect.appendChild(option);
                }
            });
            customerId.value = data.id;
        }
        if (data.hasOwnProperty('error')) {
            disabledPetOption.selected = true;
            customerId.value = null;

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
    submitForm(document.getElementById('searchCustomerForm'), contextPath + endpoints.SEARCH_CUSTOMER,
        successCallback, errorCallback);
});