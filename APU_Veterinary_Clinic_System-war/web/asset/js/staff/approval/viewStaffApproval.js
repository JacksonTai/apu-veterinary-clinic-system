const errorMessage = 'An unknown error occurred. Please try again later.';
const successCallback = (data) => {
    const { mcId, status } = data;
    Swal.fire({
        title: status,
        icon: "success"
    }).then(() => {
        window.location.href = `${contextPath}${endpoints.VIEW_STAFF_APPROVAL}?id=${mcId}`;
    });
};

const errorCallback = (error) => {
    console.error(errorMessage, error);
};

const handleButtonClick = (actionValue) => {
    document.getElementById("approvalAction").value = actionValue;
}

document.addEventListener("DOMContentLoaded", () => {
    submitForm(document.getElementById('staffApprovalForm'),
        contextPath + endpoints.UPDATE_STAFF_APPROVAL,
        successCallback, errorCallback);
});