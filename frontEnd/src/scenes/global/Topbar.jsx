
import { useContext } from "react";
import { useNavigate } from "react-router-dom";

import { Box, IconButton, useTheme } from "@mui/material";
import InputBase from "@mui/material/InputBase";
import LightModeOutLinedIcon from "@mui/icons-material/LightModeOutlined";
import DarkModeOutLinedIcon from "@mui/icons-material/DarkModeOutlined";
import NotificationsOutLinedIcon from "@mui/icons-material/NotificationsOutlined";
import SettingsOutLinedIcon from "@mui/icons-material/SettingsOutlined";
import PersonOutLinedIcon from "@mui/icons-material/PersonOutlined";
import SearchIcon from "@mui/icons-material/Search";
import Tooltip from '@mui/material/Tooltip';

import { ColorModeContext, tokens } from "../../theme";

const Topbar = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const colorMode = useContext(ColorModeContext);
  const navigateLogin = useNavigate();


  const handleLogoutButton = () => {
    navigateLogin("/login");
  }

  return <Box display="flex" justifyContent="space-between" p={2}>
    {/* Search bar */}
    <Box display="flex"
      backgroundcolor={colors.primary[400]}
      borderradius="3px"
    >
      <InputBase sx={{ ml: 2, flex: 1 }} placeholder="Search" />
      <IconButton type="button" sx={{ p: 1 }}>
        <SearchIcon />
      </IconButton>
    </Box>

    {/* ICONS */}
    <Box display="flex">
      <Tooltip title={<h2>Dark / Light mode</h2>}>
        <IconButton onClick={colorMode.toggleColorMode}>
          {theme.palette.mode === 'dark' ? (
            <DarkModeOutLinedIcon />
          ) : (
            <LightModeOutLinedIcon />
          )}
        </IconButton>
      </Tooltip>

      <IconButton>
        <NotificationsOutLinedIcon />
      </IconButton>

      <IconButton>
        <SettingsOutLinedIcon />
      </IconButton>

      <Tooltip title={<h2>Logout</h2>}>
        <IconButton onClick={handleLogoutButton}>
          <PersonOutLinedIcon />
        </IconButton>
      </Tooltip>
    </Box>
  </Box>

}

export default Topbar;
