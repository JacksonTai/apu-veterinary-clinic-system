const confirmDelete = ({entityId, entityName, deleteEndpoint, successRedirectEndpoint}) => {
    Swal.fire({
        title: `Are you sure to delete this ${entityName}?`,
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#0B5ED7",
        cancelButtonColor: "#DC3545",
        confirmButtonText: "Yes, delete it!"
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`${contextPath}${deleteEndpoint}?id=${entityId}`, {
                method: 'DELETE'
            })
                .then(response => {
                    if (response.ok) {
                        Swal.fire({
                            title: "Deleted!",
                            text: `${entityName} has been deleted.`,
                            icon: "success"
                        }).then(() => {
                            window.location.href = `${contextPath}${successRedirectEndpoint}`;
                        });
                    } else {
                        Swal.fire({
                            title: "Error!",
                            text: `Failed to delete the ${entityName}.`,
                            icon: "error"
                        });
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    Swal.fire({
                        title: "Error!",
                        text: `An error occurred while deleting the ${entityName}.`,
                        icon: "error"
                    });
                });
        }
    });
}
