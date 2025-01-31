function toggleSidebar() {
    var sidebar = document.getElementById("sidebar");
    if (sidebar.style.width === "250px" || sidebar.style.width === "") {
        sidebar.style.width = "0";
    } else {
        sidebar.style.width = "250px";
    }
}
document.addEventListener("DOMContentLoaded", function () {
    console.log("Script loaded successfully");
});
// Function to add an item to the cart
function addToCart(itemName, price) {
    const quantityInput = event.target.parentElement.querySelector(".quantity");
    const quantity = parseInt(quantityInput.value);

    if (quantity > 0) {
        const cart = JSON.parse(localStorage.getItem("cart")) || [];
        const existingItem = cart.find(item => item.name === itemName);

        if (existingItem) {
            existingItem.quantity += quantity;
        } else {
            cart.push({ name: itemName, price, quantity });
        }

        localStorage.setItem("cart", JSON.stringify(cart));
        alert(`${quantity} ${itemName}(s) added to cart!`);
    } else {
        alert("Please enter a valid quantity.");
    }
}



    // Update hidden quantity when the number input value changes
    document.querySelectorAll('.quantity').forEach(function(input) {
        input.addEventListener('input', function() {
            var quantityValue = input.value;
            var hiddenQuantityInput = input.closest('form').querySelector('.quantity-input');
            hiddenQuantityInput.value = quantityValue;
        });
    });

	const apiUrl = 'http://localhost:8080/api/restaurant';
	const restaurantForm = document.getElementById('restaurantForm');
	const restaurantList = document.getElementById('restaurantList');

	restaurantForm.addEventListener('submit', async (e) => {
	    e.preventDefault();

	    const id = document.getElementById('id').value;
	    const name = document.getElementById('name').value;
	    const description = document.getElementById('description').value;
	    const image = document.getElementById('image').files[0];

	    const formData = new FormData();
	    formData.append('name', name);
	    formData.append('description', description);
	    formData.append('image', image);

	    // If updating an existing restaurant, include its ID
	    if (id) {
	        formData.append('id', id);
	        // Send PUT request to update restaurant
	        await fetch(`${apiUrl}/${id}`, {
	            method: 'PUT',
	            body: formData,
	        });
	    } else {
	        // Send POST request to create a new restaurant
	        await fetch(apiUrl, {
	            method: 'POST',
	            body: formData,
	        });
	    }

	    restaurantForm.reset();
	    loadRestaurantItems();
	});

	async function loadRestaurantItems() {
	    const response = await fetch(apiUrl);
	    const restaurants = await response.json();
	    restaurantList.innerHTML = restaurants
	        .map(
	            (restaurant) => `
	            <div class="restaurant-item">
	                <h3>${restaurant.name}</h3>
	                <p>${restaurant.description}</p>
	                <img src="${restaurant.imageUrl}" alt="Restaurant Image" />
	                <button onclick="editRestaurant(${restaurant.id})">Edit</button>
	                <button onclick="deleteRestaurant(${restaurant.id})">Delete</button>
	            </div>
	        `
	        )
	        .join('');
	}

	async function editRestaurant(id) {
	    const response = await fetch(`${apiUrl}/${id}`);
	    const restaurant = await response.json();

	    document.getElementById('id').value = restaurant.id;
	    document.getElementById('name').value = restaurant.name;
	    document.getElementById('description').value = restaurant.description;
	}

	async function deleteRestaurant(id) {
	    await fetch(`${apiUrl}/${id}`, { method: 'DELETE' });
	    loadRestaurantItems();
	}

	// Load restaurant items when the page is loaded
	loadRestaurantItems();

