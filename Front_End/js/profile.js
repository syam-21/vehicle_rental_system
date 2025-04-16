
document.addEventListener("DOMContentLoaded", function () {
  const user = JSON.parse(localStorage.getItem("user"));
  if (!user || !user.id) {
    alert("❌ User not logged in");
    window.location.href = "login.html";
    return;
  }

  const userId = user.id;
  const nameEl = document.getElementById("name");
  const emailEl = document.getElementById("email");
  const phoneEl = document.getElementById("phone");
  const addressEl = document.getElementById("address");
  const genderEl = document.getElementById("gender");
  const bloodEl = document.getElementById("blood");
  const dobEl = document.getElementById("dob");
  const profilePic = document.getElementById("profile-pic");
  const uploadPic = document.getElementById("upload-pic");
  const uploadBtn = document.getElementById("upload-btn");
  const editBtn = document.getElementById("edit-btn");

  let userData = {};

  function fetchProfile() {
    fetch(`http://localhost:8080/api/v1/users/${userId}`)
      .then(res => res.json())
      .then(data => {
        userData = data;

        nameEl.textContent = data.name;
        emailEl.textContent = data.email;
        phoneEl.textContent = data.phone || "Not set";
        addressEl.textContent = data.address || "Not set";
        genderEl.textContent = data.gender || "Not set";
        bloodEl.textContent = data.bloodGroup || "Not set";
        dobEl.textContent = data.dateOfBirth || "Not set";

        profilePic.src = `http://localhost:8080/uploads/profile-${userId}.png?cache=${Date.now()}`;
      })
      .catch(err => {
        console.error("❌ Failed to load profile", err);
      });
  }

  fetchProfile();

  editBtn.addEventListener("click", () => {
    if (editBtn.textContent.includes("Edit")) {
      phoneEl.innerHTML = `<input type="text" id="phoneInput" value="${userData.phone || ''}">`;
      addressEl.innerHTML = `<input type="text" id="addressInput" value="${userData.address || ''}">`;

      genderEl.innerHTML = `
        <select id="genderInput">
          <option value="">Select</option>
          <option value="Male" ${userData.gender === 'Male' ? 'selected' : ''}>Male</option>
          <option value="Female" ${userData.gender === 'Female' ? 'selected' : ''}>Female</option>
          <option value="Other" ${userData.gender === 'Other' ? 'selected' : ''}>Other</option>
        </select>`;

      bloodEl.innerHTML = `
        <select id="bloodInput">
          <option value="">Select</option>
          <option value="A+" ${userData.bloodGroup === 'A+' ? 'selected' : ''}>A+</option>
          <option value="A-" ${userData.bloodGroup === 'A-' ? 'selected' : ''}>A-</option>
          <option value="B+" ${userData.bloodGroup === 'B+' ? 'selected' : ''}>B+</option>
          <option value="B-" ${userData.bloodGroup === 'B-' ? 'selected' : ''}>B-</option>
          <option value="O+" ${userData.bloodGroup === 'O+' ? 'selected' : ''}>O+</option>
          <option value="O-" ${userData.bloodGroup === 'O-' ? 'selected' : ''}>O-</option>
          <option value="AB+" ${userData.bloodGroup === 'AB+' ? 'selected' : ''}>AB+</option>
          <option value="AB-" ${userData.bloodGroup === 'AB-' ? 'selected' : ''}>AB-</option>
        </select>`;

      dobEl.innerHTML = `<input type="date" id="dobInput" value="${userData.dateOfBirth || ''}">`;

      editBtn.textContent = "💾 Save Profile";
    } else {
      const updatedData = {
        ...userData,
        phone: document.getElementById("phoneInput").value,
        address: document.getElementById("addressInput").value,
        gender: document.getElementById("genderInput").value,
        bloodGroup: document.getElementById("bloodInput").value,
        dateOfBirth: document.getElementById("dobInput").value
      };

      fetch(`http://localhost:8080/api/v1/users/${userId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(updatedData)
      })
        .then(res => {
          if (!res.ok) throw new Error("Failed to update profile");
          return res.text();
        })
        .then(msg => {
          alert("✅ Profile updated");
          editBtn.textContent = "✏️ Edit Profile";
          fetchProfile();
        })
        .catch(err => {
          console.error("❌ Error updating profile", err);
          alert("❌ Failed to update profile");
        });
    }
  });
  uploadBtn.addEventListener("click", () => {
    const file = uploadPic.files[0];
    if (!file) {
      alert("Please select a file");
      return;
    }
  
    const formData = new FormData();
    formData.append("file", file);
  
    fetch(`http://localhost:8080/api/v1/users/${userId}/upload`, {
      method: "POST",
      body: formData
    })
      .then(res => {
        if (!res.ok) throw new Error("Upload failed");
        alert("✅ Profile picture uploaded!");
        profilePic.src = `http://localhost:8080/uploads/profile-${userId}.png?cache=${Date.now()}`;
      })
      .catch(err => {
        alert("❌ Failed to upload picture");
        console.error(err);
      });
  });
  
});
