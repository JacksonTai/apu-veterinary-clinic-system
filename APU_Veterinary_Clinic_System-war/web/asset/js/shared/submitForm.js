const submitForm = (form, endpoint, successCallback, errorCallback) => {
    form.addEventListener('submit', function (event) {
        event.preventDefault();
        console.log('submitting form')
        const formData = new FormData(form);

        fetch(endpoint, {
            method: 'POST',
            body: formData,
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                if (successCallback) {
                    successCallback(data);
                }
            })
            .catch(error => {
                if (errorCallback) {
                    errorCallback(error);
                } else {
                    console.error('There was a problem with your fetch operation:', error);
                }
            });
    });
}
