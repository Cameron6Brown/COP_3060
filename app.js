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
