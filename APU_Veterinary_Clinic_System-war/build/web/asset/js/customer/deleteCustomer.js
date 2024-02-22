const confirmDelete = (customerId) => {
    Swal.fire({
        title: "Are you sure to delete this customer?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`${contextPath}${endpoints.DELETE_CUSTOMER}?id=${customerId}`, {
                method: 'DELETE'
            })
                .then(response => {
                    if (response.ok) {
                        Swal.fire({
                            title: "Deleted!",
                            text: "Customer has been deleted.",
                            icon: "success"
                        }).then(() => {
                            window.location.href = `${contextPath}${endpoints.VIEW_CUSTOMER}`;
                        });
                    } else {
                        Swal.fire({
                            title: "Error!",
                            text: "Failed to delete the customer.",
                            icon: "error"
                        });
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    Swal.fire({
                        title: "Error!",
                        text: "An error occurred while deleting the customer.",
                        icon: "error"
                    });
                });
        }
    });
}