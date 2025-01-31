const API_URL = "http://localhost:8080/api/feedbacks";

// Fetch and display feedback
async function fetchFeedbacks() {
    const response = await fetch(API_URL);
    const feedbacks = await response.json();

    const feedbackList = document.getElementById("feedbackList");
    feedbackList.innerHTML = ""; // Clear the list

    feedbacks.forEach((feedback) => {
        const feedbackItem = document.createElement("div");
        feedbackItem.className = "feedback-item";
        feedbackItem.innerHTML = `
            <p><strong>Name:</strong> ${feedback.name}</p>
            <p><strong>Rating:</strong> ${feedback.rating}</p>
            <p><strong>Review:</strong> ${feedback.review}</p>
        `;
        feedbackList.appendChild(feedbackItem);
    });
}

// Handle form submission
document.getElementById("feedbackForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const name = document.getElementById("name").value;
    const review = document.getElementById("review").value;
    const rating = document.getElementById("rating").value;

    const feedbackData = { name, review, rating };

    // Send feedback to the backend
    await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(feedbackData),
    });

    fetchFeedbacks(); // Refresh feedback list
    e.target.reset(); // Clear the form
});

// Initial load
fetchFeedbacks();
