let locations = {};

document.addEventListener("DOMContentLoaded", function () {
  fetch("assets/json/bd-locations.json")
    .then(res => res.json())
    .then(data => {
      locations = data;
      populateDivisions("pickupDivision");
      populateDivisions("dropDivision");
    });

  document.getElementById("pickupDivision").addEventListener("change", () => {
    populateDistricts("pickupDivision", "pickupDistrict");
  });

  document.getElementById("pickupDistrict").addEventListener("change", () => {
    populateUpazilas("pickupDivision", "pickupDistrict", "pickupUpazila");
  });

  document.getElementById("dropDivision").addEventListener("change", () => {
    populateDistricts("dropDivision", "dropDistrict");
  });

  document.getElementById("dropDistrict").addEventListener("change", () => {
    populateUpazilas("dropDivision", "dropDistrict", "dropUpazila");
  });
});

function populateDivisions(divisionId) {
  const select = document.getElementById(divisionId);
  select.innerHTML = "<option value=''>Select Division</option>";
  Object.keys(locations).forEach(division => {
    const option = document.createElement("option");
    option.value = division;
    option.textContent = division;
    select.appendChild(option);
  });
}

function populateDistricts(divisionId, districtId) {
  const division = document.getElementById(divisionId).value;
  const districtSelect = document.getElementById(districtId);
  districtSelect.innerHTML = "<option value=''>Select District</option>";

  if (locations[division]) {
    Object.keys(locations[division]).forEach(district => {
      const option = document.createElement("option");
      option.value = district;
      option.textContent = district;
      districtSelect.appendChild(option);
    });
  }
}

function populateUpazilas(divisionId, districtId, upazilaId) {
  const division = document.getElementById(divisionId).value;
  const district = document.getElementById(districtId).value;
  const upazilaSelect = document.getElementById(upazilaId);
  upazilaSelect.innerHTML = "<option value=''>Select Upazila</option>";

  if (locations[division] && locations[division][district]) {
    locations[division][district].forEach(upazila => {
      const option = document.createElement("option");
      option.value = upazila;
      option.textContent = upazila;
      upazilaSelect.appendChild(option);
    });
  }
}
