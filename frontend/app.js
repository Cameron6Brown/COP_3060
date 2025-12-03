// ==========================================
// Setup & Console
// ==========================================

console.log("👋 Welcome to Cameron Brown's COP_3060 JavaScript App");
console.log("📁 Repo URL: https://github.com/Cameron6Brown/COP_3060");

// ==========================================
// Variables, Types, Operators
// ==========================================

const studentName = "Cameron Brown";              // string
const graduationYear = 2025;                      // number
const isSubscribed = false;                       // boolean
const hobbies = ["Gaming", "Camping", "Coding", "Reading", "Hiking", "Traveling"]; // array
const profile = { name: "Cameron", major: "CS" }; // object
let lastVisit = undefined;                        // undefined/null

// Operators
const yearsLeft = graduationYear - 2025;          // arithmetic
const isExactYear = graduationYear === 2025;      // strict comparison
const canGraduate = isExactYear && profile.major === "CS"; // logical

// ==========================================
// DOM Elements
// ==========================================

const form = document.querySelector("form");
const emailInput = document.getElementById("email");
const moviesTable = document.querySelector("table");

// Create status div for validation feedback
const statusDiv = document.createElement("div");
statusDiv.id = "status";
form.after(statusDiv);

// Create filter dropdown for movies table
const filterDropdown = document.createElement("select");
filterDropdown.innerHTML = `
    <option value="">--Filter by Title (A-Z)--</option>
    <option value="asc">A–Z</option>
    <option value="desc">Z–A</option>
`;
moviesTable.after(filterDropdown);

// Create footer list
const footerList = document.createElement("ul");
footerList.id = "footer-list";
moviesTable.after(footerList);

// ==========================================
// Form Validation Functions
// ==========================================

// Email validation function
function validateEmail() {
    const email = emailInput.value;

    if (email.includes("@") && email.length >= 5) {
        statusDiv.textContent = "✅ Email is valid.";
        statusDiv.style.color = "green";
    } else {
        statusDiv.textContent = "❌ Email must include '@' and be at least 5 characters.";
        statusDiv.style.color = "red";
    }
}

// Form submit handler
function handleFormSubmit(event) {
    event.preventDefault();
    
    const firstName = document.getElementById("firstName").value;
    const lastName = document.getElementById("lastName").value;
    const email = emailInput.value;
    
    if (email.includes("@") && email.length >= 5) {
        statusDiv.textContent = `✅ Thank you, ${firstName} ${lastName}! Form submitted successfully.`;
        statusDiv.style.color = "green";
        console.log("Form submitted:", { firstName, lastName, email });
    } else {
        statusDiv.textContent = "❌ Please fix the errors before submitting.";
        statusDiv.style.color = "red";
    }
}

// Table filter function
function applyCompanyFilter() {
    const rows = Array.from(moviesTable.querySelectorAll("tbody tr"));
    const sortOrder = filterDropdown.value;
    
    if (sortOrder === "asc") {
        rows.sort((a, b) => a.cells[0].textContent.localeCompare(b.cells[0].textContent));
    } else if (sortOrder === "desc") {
        rows.sort((a, b) => b.cells[0].textContent.localeCompare(a.cells[0].textContent));
    }
    
    const tbody = moviesTable.querySelector("tbody");
    rows.forEach(row => tbody.appendChild(row));
}

// ==========================================
// Hobbies List (Loop Demo)
// ==========================================

const hobbiesSection = document.createElement("section");
hobbiesSection.innerHTML = "<h2>My Hobbies</h2>";
document.body.appendChild(hobbiesSection);

const hobbyList = document.createElement("ul");
hobbyList.id = "hobby-list";
hobbiesSection.appendChild(hobbyList);

for (let i = 0; i < hobbies.length; i++) {
    const li = document.createElement("li");
    li.textContent = hobbies[i];
    hobbyList.appendChild(li);
}

// ==========================================
// Fetch (AJAX) - Random Users API
// ==========================================

const fetchSection = document.createElement("section");
fetchSection.innerHTML = "<h2>Random Users (API Demo)</h2>";
document.body.appendChild(fetchSection);

const fetchButton = document.createElement("button");
fetchButton.textContent = "Load Users";
fetchSection.appendChild(fetchButton);

const filterSelect = document.createElement("select");
filterSelect.innerHTML = `
  <option value="">-- Sort Users --</option>
  <option value="asc">A–Z</option>
  <option value="desc">Z–A</option>
`;
fetchSection.appendChild(filterSelect);

const userList = document.createElement("ul");
userList.id = "user-list";
fetchSection.appendChild(userList);

let usersData = [];

// Render users list
function renderUsers(users) {
    userList.innerHTML = "";
    if (users.length === 0) {
        userList.innerHTML = "<li>No users found.</li>";
        return;
    }
    const itemsToRender = users.slice(0, 10);
    itemsToRender.forEach(user => {
        const li = document.createElement("li");
        li.textContent = `${user.name.first} ${user.name.last} (${user.email})`;
        userList.appendChild(li);
    });
}

// Fetch users from API
async function fetchUsers() {
    userList.innerHTML = "<li>Loading…</li>";
    try {
        const response = await fetch("https://randomuser.me/api/?results=20");
        if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
        const data = await response.json();
        usersData = data.results;
        renderUsers(usersData);
    } catch (error) {
        userList.innerHTML = `<li style="color:red;">Error: ${error.message}</li>`;
    }
}

// Apply user filter/sort
function applyFilter() {
    let sortedUsers = [...usersData];
    const sortOrder = filterSelect.value;
    if (sortOrder === "asc") {
        sortedUsers.sort((a, b) => a.name.first.localeCompare(b.name.first));
    } else if (sortOrder === "desc") {
        sortedUsers.sort((a, b) => b.name.first.localeCompare(a.name.first));
    }
    renderUsers(sortedUsers);
}

// ==========================================
// Utility Functions
// ==========================================

const data = [
    { id: 1, name: "Alice", score: 85 },
    { id: 2, name: "Bob", score: 92 },
    { id: 3, name: "Charlie", score: 78 },
    { id: 4, name: "Diana", score: 90 }
];

// Build URL with query parameters
function buildUrl(base, queryParams = {}) {
    const queryString = Object.entries(queryParams)
        .map(([key, val]) => `${encodeURIComponent(key)}=${encodeURIComponent(val)}`)
        .join("&");
    return queryString ? `${base}?${queryString}` : base;
}

// Render list items into DOM
function renderList(items, containerId) {
    const container = document.getElementById(containerId);
    if (!container) {
        console.warn(`Container "${containerId}" not found.`);
        return;
    }
    container.innerHTML = "";

    if (items.length === 0) {
        container.textContent = "No items to display.";
        return;
    }

    const ul = document.createElement("ul");
    items.forEach(item => {
        const li = document.createElement("li");
        li.textContent = `${item.name} (Score: ${item.score})`;
        ul.appendChild(li);
    });
    container.appendChild(ul);
}

// Filter data by minimum score
function filterData(items, minScore) {
    return items.filter(item => item.score >= minScore);
}

// ==========================================
// Event Listeners
// ==========================================

form.addEventListener("submit", handleFormSubmit);
emailInput.addEventListener("input", validateEmail);
filterDropdown.addEventListener("change", applyCompanyFilter);
fetchButton.addEventListener("click", fetchUsers);
filterSelect.addEventListener("change", applyFilter);

// ==========================================
// Initialize
// ==========================================

const url = buildUrl("https://api.example.com/users", { minScore: 80 });
console.log("Fetch URL:", url);

const filteredData = filterData(data, 80);
renderList(filteredData, "output");

console.log("✅ App initialized successfully!");
