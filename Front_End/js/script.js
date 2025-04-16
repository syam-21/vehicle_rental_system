document.addEventListener("DOMContentLoaded", () => {
    const toggleBtn = document.getElementById("toggleMode");
    const currentMode = localStorage.getItem("theme");
  
    if (currentMode === "light") {
      document.body.classList.add("light-mode");
      toggleBtn.textContent = "🌞";
    }
  
    toggleBtn?.addEventListener("click", () => {
      document.body.classList.toggle("light-mode");
      const isLight = document.body.classList.contains("light-mode");
      toggleBtn.textContent = isLight ? "🌞" : "🌙";
      localStorage.setItem("theme", isLight ? "light" : "dark");
    });
  });
  