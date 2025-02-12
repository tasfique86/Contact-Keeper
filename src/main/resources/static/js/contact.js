    console.log("Contact Js");

    const viewContactModal = document.getElementById('view_contact_modal');

    const baseURL = 'http://localhost:8080';
    // options with default values
    const options = {
        placement: 'bottom-right',
        backdrop: 'dynamic',
        backdropClasses:
            'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
        closable: true,
        onHide: () => {
            console.log('modal is hidden');
        },
        onShow: () => {
            console.log('modal is shown');
        },
        onToggle: () => {
            console.log('modal has been toggled');
        },
    };

    // instance options object
    const instanceOptions = {
        id: 'view_contact_modal',
        override: true
    };

    const contactModal =new Modal (viewContactModal, options, instanceOptions);

    function openContactModal() {
        contactModal.show();
    }
    function closeContactModal() {
        contactModal.hide();
    }

    async function loadContactData(id){

        // console.log(id);
        // console.log("loadContactData");
       try{
           const data= await (await fetch(`${baseURL}/api/contacts/${id}`)).json();
           console.log(data);
           document.querySelector('#contact_name').innerHTML = data.name;
           document.querySelector('#contact_email').innerHTML = data.email;
           document.querySelector('#contact_address').innerHTML = data.address;
           document.querySelector('#contact_about').innerHTML = data.description;
           document.querySelector('#contact_image').src= data.picture;


           const contactFavorite = document.querySelector("#contact_favorite");
           if (data.favorite) {
               contactFavorite.innerHTML = '<i class="fa-solid fa-heart text-re-400"></i>';
               contactFavorite.style.display = "block";  // Show if favorite
           } else {
               contactFavorite.style.display = "none";   // Hide if not favorite
           }

           document.querySelector("#contact_linkedIn").href = data.linkdInLink || "#";
           document.querySelector("#contact_facebook").href = data.facebookLink || "#";
           document.querySelector('#contact_phone').innerHTML= data.phoneNumber;
           openContactModal()
       }catch (e) {
           console.error("Error :",e);

       }



    }


    //delete contact
    async function deleteContact(id) {
        Swal.fire({
            title: "Do you want to delete the contact?",
            icon: "warning",
            iconColor: "#ff0000",
            showCancelButton: true,
            confirmButtonText: "Delete",
            confirmButtonColor: "#3085d6", // Blue color for the confirm button
            cancelButtonColor: "#d33",    // Red color for the cancel button
            cancelButtonText: "Cancel",   // Optional: Custom text for the cancel button
        }).then((result) => {
            if (result.isConfirmed) {
                const url = `${baseURL}/user/contacts/delete/` + id;
                window.location.replace(url);
            }
        });
    }


    //message for link
    function handleSocialClick(element, platform) {
        let url = element.getAttribute("data-url");
        if (!url || url.trim() === "" || url === "null") {
            Swal.fire({
                title: "Not Available",
                text: platform + " link is not available.",
                icon: "warning",
                confirmButtonText: "OK"  // Ensures it stays until clicked
            });
            return false; // Prevents navigation
        } else {
            window.open(url, "_blank"); // Open link in a new tab if available
        }
    }
