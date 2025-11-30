const imageInput = document.getElementById("imageInput");
const topTextInput = document.getElementById("topText");
const bottomTextInput = document.getElementById("bottomText");
const canvas = document.getElementById("memeCanvas");
const ctx = canvas.getContext("2d");
let baseImage = new Image();
let savedMemeUrl = ""; // Store the last saved meme URL for sharing

// Text control elements
const topFont = document.getElementById("topFont");
const topFontSize = document.getElementById("topFontSize");
const topColor = document.getElementById("topColor");
const topOutlineColor = document.getElementById("topOutlineColor");
const topPosition = document.getElementById("topPosition");

const bottomFont = document.getElementById("bottomFont");
const bottomFontSize = document.getElementById("bottomFontSize");
const bottomColor = document.getElementById("bottomColor");
const bottomOutlineColor = document.getElementById("bottomOutlineColor");
const bottomPosition = document.getElementById("bottomPosition");

// When user selects an image
imageInput.addEventListener("change", (e) => {
    const file = e.target.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = function(event) {
        baseImage = new Image();
        baseImage.onload = () => {
            canvas.width = baseImage.width;
            canvas.height = baseImage.height;
            drawMeme();
        };
        baseImage.src = event.target.result;
    };
    reader.readAsDataURL(file);
});

// Draw meme function with custom styling
function drawMeme() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    if (baseImage) {
        ctx.drawImage(baseImage, 0, 0, canvas.width, canvas.height);
    }

    ctx.textAlign = "center";
    ctx.lineWidth = 4;

    // Draw top text
    ctx.font = `bold ${topFontSize.value}px ${topFont.value}`;
    ctx.fillStyle = topColor.value;
    ctx.strokeStyle = topOutlineColor.value;
    ctx.fillText(topTextInput.value, canvas.width / 2, parseInt(topPosition.value));
    ctx.strokeText(topTextInput.value, canvas.width / 2, parseInt(topPosition.value));

    // Draw bottom text
    ctx.font = `bold ${bottomFontSize.value}px ${bottomFont.value}`;
    ctx.fillStyle = bottomColor.value;
    ctx.strokeStyle = bottomOutlineColor.value;
    ctx.fillText(bottomTextInput.value, canvas.width / 2, canvas.height - parseInt(bottomPosition.value));
    ctx.strokeText(bottomTextInput.value, canvas.width / 2, canvas.height - parseInt(bottomPosition.value));
}

// Update preview when any control changes
document.getElementById("addTextBtn").addEventListener("click", drawMeme);
[topFont, topFontSize, topColor, topOutlineColor, topPosition,
 bottomFont, bottomFontSize, bottomColor, bottomOutlineColor, bottomPosition].forEach(el => {
    el.addEventListener("change", drawMeme);
    el.addEventListener("input", drawMeme);
});

// Download meme
document.getElementById("downloadBtn").addEventListener("click", () => {
    const link = document.createElement("a");
    link.download = "meme.png";
    link.href = canvas.toDataURL("image/png");
    link.click();
});

// Save to server as base64
document.getElementById("saveServerBtn").addEventListener("click", async () => {
    const imageData = canvas.toDataURL("image/png");

    const response = await fetch("http://localhost:8080/api/memes/saveBase64", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            data: imageData,
            topText: topTextInput.value,
            bottomText: bottomTextInput.value
        })
    });

    if (response.ok) {
        const savedMeme = await response.json();
        savedMemeUrl = `http://localhost:8080/uploads/${savedMeme.filename}`;
        alert("Meme saved to server! You can now share it.");
    } else {
        alert("Error saving meme.");
    }
});

// === SOCIAL SHARING FEATURES ===

// Share on Twitter
document.getElementById("shareTwitter").addEventListener("click", () => {
    if (!savedMemeUrl) {
        alert("Please save your meme to the server first!");
        return;
    }
    const text = encodeURIComponent("Check out my meme!");
    const url = encodeURIComponent(savedMemeUrl);
    window.open(`https://twitter.com/intent/tweet?text=${text}&url=${url}`, "_blank");
});

// Share on Facebook
document.getElementById("shareFacebook").addEventListener("click", () => {
    if (!savedMemeUrl) {
        alert("Please save your meme to the server first!");
        return;
    }
    const url = encodeURIComponent(savedMemeUrl);
    window.open(`https://www.facebook.com/sharer/sharer.php?u=${url}`, "_blank");
});

// Share on Reddit
document.getElementById("shareReddit").addEventListener("click", () => {
    if (!savedMemeUrl) {
        alert("Please save your meme to the server first!");
        return;
    }
    const title = encodeURIComponent("Check out my meme!");
    const url = encodeURIComponent(savedMemeUrl);
    window.open(`https://reddit.com/submit?title=${title}&url=${url}`, "_blank");
});

// Copy link to clipboard
document.getElementById("copyLink").addEventListener("click", async () => {
    if (!savedMemeUrl) {
        alert("Please save your meme to the server first!");
        return;
    }
    try {
        await navigator.clipboard.writeText(savedMemeUrl);
        alert("Link copied to clipboard!");
    } catch (err) {
        // Fallback for older browsers
        const input = document.createElement("input");
        input.value = savedMemeUrl;
        document.body.appendChild(input);
        input.select();
        document.execCommand("copy");
        document.body.removeChild(input);
        alert("Link copied to clipboard!");
    }
});