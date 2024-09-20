import { TextField, FormControl, InputLabel, Select, MenuItem, FormHelperText } from "@mui/material";
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import dayjs from 'dayjs';
import Divider from '@mui/material/Divider';

const OrdersFormInput = ({ handleBlur, handleChange, values, touched,
  errors, ordersSchema }) => {

  // console.log("values.date", values.date);

  return (
    <>

      <TextField
        disabled
        variant="filled"
        type="text"
        label="Id"
        value={values.id}
        name="id"
        sx={{ gridColumn: "span 1" }}
      />

      <FormControl
        variant="filled"
        sx={{ gridColumn: "span 1" }}
      >
        <LocalizationProvider dateAdapter={AdapterDayjs}>
          <DemoContainer
            components={['DatePicker']}
            sx={{ gridColumn: "span 1" }}
          >
            <DatePicker
              disabled
              label="Date"
              disablePast
              value={values.date}
            />
          </DemoContainer>
        </LocalizationProvider>
      </FormControl>

    </>
  )

}

export default OrdersFormInput;
