function populateBDLocations(card, type) {
    const divisionDropdown = card.querySelector(`.${type}-division`);
    const districtDropdown = card.querySelector(`.${type}-district`);
    const upazilaDropdown = card.querySelector(`.${type}-upazila`);
  
    fetch("assets/data/bd-locations.json")
      .then(res => res.json())
      .then(data => {
        // Fill Divisions
        divisionDropdown.innerHTML = "<option value=''>Select Division</option>";
        Object.keys(data).forEach(division => {
          divisionDropdown.innerHTML += `<option value="${division}">${division}</option>`;
        });
  
        // On Division → fill District
        divisionDropdown.addEventListener("change", () => {
          const selectedDivision = divisionDropdown.value;
          districtDropdown.innerHTML = "<option value=''>Select District</option>";
          upazilaDropdown.innerHTML = "<option value=''>Select Upazila</option>";
  
          if (data[selectedDivision]) {
            Object.keys(data[selectedDivision]).forEach(district => {
              districtDropdown.innerHTML += `<option value="${district}">${district}</option>`;
            });
          }
        });
  
        // On District → fill Upazila
        districtDropdown.addEventListener("change", () => {
          const selectedDivision = divisionDropdown.value;
          const selectedDistrict = districtDropdown.value;
          upazilaDropdown.innerHTML = "<option value=''>Select Upazila</option>";
  
          if (data[selectedDivision][selectedDistrict]) {
            data[selectedDivision][selectedDistrict].forEach(upazila => {
              upazilaDropdown.innerHTML += `<option value="${upazila}">${upazila}</option>`;
            });
          }
        });
      })
      .catch(err => {
        console.error("Failed to load BD location data:", err);
      });
  }
  