import { Box } from "@mui/material";
import Header from "../../components/Header";
import BarChart from "../../components/BarChart";

const Bar = () => {
  return (
    <Box m="20px">
      <Header title="Bar Chart" subtitle="Top 10 countries and products in terms of sales" />
      <Box height="75vh">
        <BarChart />
      </Box>
    </Box>
  )
}

export default Bar;
