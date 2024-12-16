
const RefreshToken = async () => {
  const url = import.meta.env.VITE_URL_PREFIX;

  const token = localStorage.getItem("token");
  const expires = localStorage.getItem("expires");

  if (!token || !expires) return;

  const timeRemaining = new Date(expires).getTime() - new Date().getTime();

  if (timeRemaining < 2 * 60 * 1000) {
    try {
      const response = await fetch(url + "auth/refresh", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        console.error("Fail to refresh token");
        return;
      }

      const data = await response.json();

      localStorage.setItem("token", data.token);
      localStorage.setItem("expires", data.expires);

      console.log("Token refreshed");
    } catch (error) {
      console.error("Fail to request token", error);
    }
  }

};

export default RefreshToken;
