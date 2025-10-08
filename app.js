// Setup & Console

console.log("👋 Welcome to Cameron Brown's COP_3060 JavaScript App");
console.log("📁 Repo URL: https://github.com/Cameron6Brown/COP_3060");

// Variables, Types, Operators

const studentName = "Cameron Brown";            // string
const graduationYear = 2025;                    // number
const isSubscribed = false;                     // boolean
const hobbies = ["Gaming", "Camping", "Coding"]; // array
const profile = { name: "Cameron", major: "CS" }; // object
let lastVisit = undefined;                      // undefined/null

// Operators
const yearsLeft = graduationYear - 2025;        // arithmetic
const isExactYear = graduationYear === 2025;    // strict comparison
const canGraduate = isExactYear && profile.major === "CS"; // logical

// DOM Elements

const form = document.querySelector("form");
const emailInput = document.getElementById("email");
const statusDiv = document.createElement("div");
statusDiv.id = "status";
form.after(statusDiv);

const moviesTable = document.querySelector("table");
const footerList = document.createElement("ul");
moviesTable.after(footerList);

const filterDropdown = document.createElement("select");
filterDropdown.innerHTML = `
    <option value="">--Filter by Company (A-Z)--</option>
    <option value="asc">A–Z</option>
    <option value="desc">Z–A</option>
`;
moviesTable.after(filterDropdown);

// Event Listeners

form.addEventListener("submit", handleFormSubmit);
emailInput.addEventListener("input", validateEmail);
filterDropdown.addEventListener("change", applyCompanyFilter);

// Control Flow (Validation + Loop)


const statusDiv = document.createElement("div");
statusDiv.id = "status";
document.querySelector("form").after(statusDiv);


const emailInput = document.getElementById("email");
emailInput.addEventListener("input", () => {
    const email = emailInput.value;

    if (email.includes("@") && email.length >= 5) {
        statusDiv.textContent = "✅ Email is valid.";
        statusDiv.style.color = "green";
    } else {
        statusDiv.textContent = "❌ Email must include '@' and be at least 5 characters.";
        statusDiv.style.color = "red";
    }
});

const hobbies = ["Gaming", "Camping", "Coding", "Reading", "Hiking", "Traveling"];
const hobbyList = document.createElement("ul");
document.body.appendChild(hobbyList);

for (let i = 0; i < hobbies.length; i++) {
    const li = document.createElement("li");
    li.textContent = hobbies[i];
    hobbyList.appendChild(li);
}

// Fetch (AJAX)

const fetchButton = document.createElement("button");
fetchButton.textContent = "Load Users";
document.body.appendChild(fetchButton);

const userList = document.createElement("ul");
document.body.appendChild(userList);

const filterSelect = document.createElement("select");
filterSelect.innerHTML = `
  <option value="">-- Sort Users A–Z --</option>
  <option value="asc">A–Z</option>
  <option value="desc">Z–A</option>
`;
document.body.appendChild(filterSelect);

let usersData = []; // Array to store fetched users

// Function to render users list
function renderUsers(users) {
  userList.innerHTML = ""; // Clear current list
  if (users.length === 0) {
    userList.innerHTML = "<li>No users found.</li>";
    return;
  }
  const itemsToRender = users.slice(0, 10); // Render up to 10 items
  itemsToRender.forEach(user => {
    const li = document.createElement("li");
    li.textContent = `${user.name.first} ${user.name.last} (${user.email})`;
    userList.appendChild(li);
  });
}

// Function to fetch users
async function fetchUsers() {
  userList.innerHTML = "<li>Loading…</li>";
  try {
    const response = await fetch("https://randomuser.me/api/?results=20"); // Public, no-auth API
    if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
    const data = await response.json();
    usersData = data.results;
    renderUsers(usersData);
  } catch (error) {
    userList.innerHTML = `<li style="color:red;">Error: ${error.message}</li>`;
  }
}

// Function to apply filter/sort
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

// Event listeners
fetchButton.addEventListener("click", fetchUsers);
filterSelect.addEventListener("change", applyFilter);
