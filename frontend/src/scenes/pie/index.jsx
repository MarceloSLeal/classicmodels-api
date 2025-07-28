import { Box } from "@mui/material";
import Header from "../../components/Header";
import PieChart from "../../components/PieChart";

const Pie = () => {
  return (
    <Box m="20px">
      <Header title="Pie Chart" subtitle="Top 10 countries in terms of sales" />
      <Box height="75vh">
        <PieChart />
      </Box>
    </Box>
  )
}

export default Pie;
