
document.getElementById('startNappi').onclick = function(){
  document.getElementById("overlay").style.display = 'none';
  document.getElementById("myCanvas").style.display = 'block';
  alkutoimet(10);
};


//matokuolee
function overlay2 (){
    document.getElementById("myCanvas").style.display = 'none';
    document.getElementById("overlay2").style.display = 'block';
    document.getElementById("score").innerHTML = "Score: " + mato.pituus;
}

document.getElementById('pelaaUudestaan').onclick = function () {
    document.getElementById("myCanvas").style.display = 'block';
    document.getElementById("overlay2").style.display = 'none';
    alkutoimet(10);
};

document.getElementById('tallennaTiedot').onclick = function (){
    document.getElementById("overlay2").style.display = 'none';
    document.getElementById("scoreForm").style.display = "block";
};