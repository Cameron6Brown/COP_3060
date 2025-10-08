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
