import { DataGrid, GridToolbarContainer } from "@mui/x-data-grid";
import { Box } from "@mui/material";
import AddIcon from '@mui/icons-material/Add';
import Button from '@mui/material/Button';
import { useNavigate } from "react-router-dom";

const BoxDataGrid = ({ colors, rows, columns, path, rowId, rowHeight }) => {
  const navigateAdd = useNavigate();

  const EditToolbar = () => {
    const handleClick = () => {
      navigateAdd(path);
    }

    return (
      <GridToolbarContainer>
        <Button
          sx={{
            backgroundColor: colors.blueAccent[700],
            color: colors.primary[100],
            paddingTop: '10px', paddingRight: '10px',
            paddingBottom: '10px', paddingLeft: '10px',
          }}
          startIcon={<AddIcon />} onClick={handleClick}>
          Add record
        </Button>
      </GridToolbarContainer>
    );
  }

  return (

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
        "& .MuiDataGrid-toolbarContainer .MuiButton-text": {
          color: `${colors.grey[100]} !important`,
        },
      }}
    >
      <DataGrid
        rows={rows}
        columns={columns}
        rowHeight={rowHeight || undefined}
        slots={{
          toolbar: EditToolbar,
        }}
        {...(rowId ? { getRowId: (row) => row[rowId] } : {})}
      />
    </Box>

  )
}

export default BoxDataGrid;
