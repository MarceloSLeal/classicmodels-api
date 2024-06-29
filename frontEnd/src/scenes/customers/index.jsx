import { Box, useTheme } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
import { tokens } from "../../theme";
import { mockDataCustomers } from "../../data/mockDataCustomers";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";

const Customers = () => {
    const theme = useTheme();
    const colors = tokens(theme.palette.mode);
    const url = Urls("oi");

    console.log(url.customers.findAll);


    const columns = [
        { field: "id", headerName: "ID", flex: 0.5 },
        { field: "name", headerName: "NAME", flex: 1, cellClassName: "name-column--cell" },
        { field: "email", headerName: "EMAIL", flex: 1 },
        { field: "contactLastName", headerName: "CONT.LAST NAME", flex: 1 },
        { field: "contactFirstName", headerName: "CONT.FIRST NAME", flex: 1 },
        { field: "phone", headerName: "PHONE", flex: 1 },
        { field: "addressLine1", headerName: "ADRESS1", flex: 1 },
        { field: "addressLine2", headerName: "ADRESS2", flex: 1 },
        { field: "city", headerName: "CITY", flex: 1 },
        { field: "state", headerName: "STATE", flex: 1 },
        { field: "postalCode", headerName: "P.CODE", flex: 1 },
        { field: "country", headerName: "COUNTRY", flex: 1 },
        { field: "creditLimit", headerName: "CREDIT", flex: 1 },
        { field: "employeeId", headerName: "EMP.ID", flex: 1 },
    ];

    return (
        <Box m="20px">
            <Header title="CUSTOMERS" subtitle="Managing Customers" />
            <Box
                m="40px 0 0 0"
                height="75vh"
                sx={{
                    "& .MuiDataGrid-root": {
                        border: "none",
                    },
                    "& .MuiDataGrid-cell": {
                        borderBottom: "none",
                    },
                    "& .name-column--cell": {
                        color: colors.greenAccent[300],
                    },
                    "& .MuiDataGrid-columnHeader": {
                        backgroundColor: colors.blueAccent[700],
                        borderBottom: "none",
                        fontSize: 14
                    },
                    "& .MuiDataGrid-virtualScroller": {
                        backgroundColor: colors.primary[400],
                    },
                    "& .MuiDataGrid-footerContainer": {
                        borderTop: "none",
                        backgroundColor: colors.blueAccent[700],
                    },
                }}
            >
                <DataGrid
                    rows={mockDataCustomers}
                    columns={columns}
                />
            </Box>
        </Box>
    )
}

export default Customers;
