const apiUrl = 'http://localhost:8080/api/menu';
const menuForm = document.getElementById('menuForm');
const menuList = document.getElementById('menuList');

menuForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = document.getElementById('id').value;
    const name = document.getElementById('name').value;
    const description = document.getElementById('description').value;
    const price = document.getElementById('price').value;
    const inStock = document.getElementById('inStock').checked;

    if (id) {
        await fetch(`${apiUrl}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(menuItem),
        });
    } else {
        await fetch(apiUrl, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(menuItem),
        });
    }

    menuForm.reset();
    loadMenuItems();
});

async function loadMenuItems() {
    const response = await fetch(apiUrl);
    const menuItems = await response.json();
    menuList.innerHTML = menuItems
        .map(
            (item) => `
        <div>
            <h3>${item.name}</h3>
            <p>${item.description}</p>
            <p>Price: $${item.price}</p>
            <p>${item.inStock ? 'In Stock' : 'Out of Stock'}</p>
            <button onclick="editMenuItem(${item.id})">Edit</button>
            <button onclick="deleteMenuItem(${item.id})">Delete</button>
        </div>
    `
        )
        .join('');
}

async function editMenuItem(id) {
    const response = await fetch(`${apiUrl}/${id}`);
    const menuItem = await response.json();
    document.getElementById('id').value = menuItem.id;
    document.getElementById('name').value = menuItem.name;
    document.getElementById('description').value = menuItem.description;
    document.getElementById('price').value = menuItem.price;
    document.getElementById('inStock').checked = menuItem.inStock;
}

async function deleteMenuItem(id) {
    await fetch(`${apiUrl}/${id}`, { method: 'DELETE' });
    loadMenuItems();
}

loadMenuItems();
document.addEventListener("DOMContentLoaded", function() {
    // Function to update the cart when an item is added
    function updateCart() {
        let cartItems = JSON.parse(localStorage.getItem("cartItems")) || []; // Get the current cart items from localStorage
        let cartContainer = document.getElementById("cartContainer");
        
        // Clear the existing items in the cart
        cartContainer.innerHTML = "";

        // Display the items from the cart
        cartItems.forEach(item => {
            let cartItemDiv = document.createElement("div");
            cartItemDiv.classList.add("cart-item");

            let itemName = document.createElement("h3");
            itemName.textContent = item.name;
            cartItemDiv.appendChild(itemName);

            let itemDescription = document.createElement("p");
            itemDescription.textContent = item.description;
            cartItemDiv.appendChild(itemDescription);

            let itemPrice = document.createElement("p");
            itemPrice.textContent = "$" + item.price;
            cartItemDiv.appendChild(itemPrice);

           
            cartContainer.appendChild(cartItemDiv);
        });
    }

    // Add item to cart
    function addItemToCart(item) {
        let cartItems = JSON.parse(localStorage.getItem("cartItems")) || [];
        cartItems.push(item);  // Add the new item to the cart
        localStorage.setItem("cartItems", JSON.stringify(cartItems));  // Save the updated cart to localStorage
        updateCart();  // Update the cart display
    }

    // Handle checkout button click
    document.getElementById("checkoutButton").addEventListener("click", function() {
        let cartItems = JSON.parse(localStorage.getItem("cartItems")) || [];
        
        if(cartItems.length === 0) {
            alert("Your cart is empty. Add items before checking out.");
        } else {
            // Perform checkout logic (e.g., navigate to checkout page)
            window.location.href = "/checkout";  // Redirect to checkout page
        }
    });

    // Initialize the cart when the page is loaded
    updateCart();

});

