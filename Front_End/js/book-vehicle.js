document.addEventListener("DOMContentLoaded", function () {
  const user = JSON.parse(localStorage.getItem("user"));
  const vehicleId = localStorage.getItem("selectedVehicleId");

  if (!user || !vehicleId) {
    alert("Login or vehicle selection missing");
    return;
  }

  // Load JSON locations
  fetch("assets/json/bd-locations.json")
    .then(res => res.json())
    .then(data => {
      window.bdLocations = data;
      populateDropdowns("pickup", data);
      populateDropdowns("drop", data);
    });

  document.getElementById("pickupDate").addEventListener("change", calculateFare);
  document.getElementById("returnDate").addEventListener("change", calculateFare);
});

function populateDropdowns(prefix, data) {
  const division = document.getElementById(`${prefix}Division`);
  const district = document.getElementById(`${prefix}District`);
  const upazila = document.getElementById(`${prefix}Upazila`);

  division.innerHTML = `<option disabled selected>Select Division</option>`;
  for (let div in data) {
    const option = document.createElement("option");
    option.value = div;
    option.textContent = div;
    division.appendChild(option);
  }

  division.addEventListener("change", () => {
    district.innerHTML = `<option disabled selected>Select District</option>`;
    const selectedDiv = division.value;
    for (let dist in data[selectedDiv]) {
      const option = document.createElement("option");
      option.value = dist;
      option.textContent = dist;
      district.appendChild(option);
    }
  });

  district.addEventListener("change", () => {
    upazila.innerHTML = `<option disabled selected>Select Upazila</option>`;
    const selectedDiv = division.value;
    const selectedDist = district.value;
    const upazilas = data[selectedDiv][selectedDist];
    upazilas.forEach(upa => {
      const option = document.createElement("option");
      option.value = upa;
      option.textContent = upa;
      upazila.appendChild(option);
    });
  });
}

function calculateFare() {
  const pickupDate = new Date(document.getElementById("pickupDate").value);
  const returnDate = new Date(document.getElementById("returnDate").value);
  const diffTime = returnDate - pickupDate;
  const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

  const vehicleId = localStorage.getItem("selectedVehicleId");

  if (diffDays > 0 && vehicleId) {
    fetch(`http://localhost:8080/api/v1/vehicles/${vehicleId}`)
      .then(res => res.json())
      .then(vehicle => {
        if (vehicle && vehicle.pricePerDay) {
          const fare = diffDays * vehicle.pricePerDay;
          document.getElementById("fareDisplay").textContent = `Fare: ${fare} BDT`;
          document.getElementById("fareDisplay").setAttribute("data-fare", fare);
        }
      });
  } else {
    document.getElementById("fareDisplay").textContent = "Fare: 0 BDT";
    document.getElementById("fareDisplay").setAttribute("data-fare", 0);
  }
}

function submitBooking() {
  const user = JSON.parse(localStorage.getItem("user"));
  const vehicleId = localStorage.getItem("selectedVehicleId");

  if (!user || !vehicleId) {
    alert("Login or vehicle selection missing");
    return;
  }

  const fare = parseFloat(document.getElementById("fareDisplay").getAttribute("data-fare"));

  const dto = {
    userId: user.id,
    vehicleId: vehicleId,
    pickupDate: document.getElementById("pickupDate").value,
    returnDate: document.getElementById("returnDate").value,
    fare: fare,  // ✅ added fare here
    pickup: {
      division: document.getElementById("pickupDivision").value,
      district: document.getElementById("pickupDistrict").value,
      upazila: document.getElementById("pickupUpazila").value,
      address: "Customer Pickup Point"
    },
    drop: {
      division: document.getElementById("dropDivision").value,
      district: document.getElementById("dropDistrict").value,
      upazila: document.getElementById("dropUpazila").value,
      address: "Customer Drop Point"
    }
  };

  fetch("http://localhost:8080/api/v1/bookings/book", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(dto)
  })
  .then(res => {
    if (!res.ok) throw new Error("Booking failed");
    return res.json();
  })
  .then(data => {
    alert("✅ " + data.message);
    window.location.href = "dashboard.html";
  })
  .catch(err => {
    console.error(err);
    alert("❌ Booking failed.");
  });
}
