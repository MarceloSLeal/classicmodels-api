import { useAuth } from "./AuthContext";

const RefreshToken = async () => {
  const { logout } = useAuth();

  const url = import.meta.env.VITE_URL_PREFIX;

  const response = await fetch(url + "auth/refresh", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include",
  });

  if (!response.ok) {
    console.error("Fail to refresh token");
    logout();
  } else {
    console.log("Token refreshed");
  }
};

export default RefreshToken;
