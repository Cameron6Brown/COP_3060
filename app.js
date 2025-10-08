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
