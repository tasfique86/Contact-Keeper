console.log("Script loaded!");

let currentTheme=getTheme();

document.addEventListener("DOMContentLoaded", () => {
    changeTheme();
});


function changeTheme(){


    changePageTheme(currentTheme,currentTheme);

    const changeThemeBtn = document.querySelector("#them_change_button");


    changeThemeBtn.addEventListener("click", () => {

        const oldTheme = currentTheme;
        console.log("changeTheme");

        if(currentTheme === "dark"){
            currentTheme="light";
        }
        else {
            currentTheme = "dark";
        }

        changePageTheme(currentTheme,oldTheme);
    });
}


function setTheme(theme){
    localStorage.setItem("theme", theme);
}

function getTheme(){
    let theme = localStorage.getItem("theme");
    if(theme) return   theme;
    else  return "light";
}



function changePageTheme(theme,oldTheme){
    setTheme(theme);

    document.querySelector("html").classList.remove(oldTheme);
    document.querySelector("html").classList.add(theme);
    document.querySelector("#them_change_button").querySelector("span").textContent = currentTheme === "light" ? "Dark" : "Light";

}
